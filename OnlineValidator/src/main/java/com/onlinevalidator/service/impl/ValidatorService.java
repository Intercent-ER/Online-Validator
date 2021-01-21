package com.onlinevalidator.service.impl;

import com.onlinevalidator.controller.CatalogController;
import com.onlinevalidator.generatedsources.xsd.FailedAssert;
import com.onlinevalidator.generatedsources.xsd.SchematronOutput;
import com.onlinevalidator.model.OvCatalog;
import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.model.OvValidatore;
import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import com.onlinevalidator.model.enumerator.TipoFileEnum;
import com.onlinevalidator.pojo.ValidationAssert;
import com.onlinevalidator.pojo.ValidationReport;
import com.onlinevalidator.repository.OvCatalogJpaRepository;
import com.onlinevalidator.repository.OvTipoDocumentoJpaRepository;
import com.onlinevalidator.service.ConfigurazioneServiceInterface;
import com.onlinevalidator.service.LocalServiceUriResolverInterface;
import com.onlinevalidator.service.ValidatorServiceInterface;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.annotation.PostConstruct;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ValidatorService implements ValidatorServiceInterface {

	private static final String XML_CATALOG_XML = "xml-catalog.xml";
	private static final String CATALOG_BASE_URL = CatalogController.CATALOG_BASE_URL + ".html";
	private static final boolean OWASP_FEATURES_TO_DISALLOW_XXE_PAR_VALUE = false;
	private static final boolean OWASP_FEATURES_TO_DISALLOW_XXE_ENT_VALUE = false;
	private static final boolean OWASP_FEATURES_TO_DISALLOW_DOCTYPE_VALUE = true;
	private static final String LOGGING_KEY_VALUE = "{}={}";

	// Mappa che contiene i validatori XSD gestiti in cache (chiave: Validatore.id; valore: Schema)
	private Map<Integer, Schema> cacheXsd;

	// Mappa che contiene i validatori SCH gestiti in cache (chiave: Validatore.id; valore: Templates)
	private Map<Integer, Templates> cacheSchematron;

	// Mappa dei parametri da impostare sugli XSLT
	private Map<String, String> xsltParameters;

	private URL urlFileXmlCatalog;

	@Autowired
	private OvCatalogJpaRepository catalogJpaRepository;
	public static final boolean NAMESPACE_AWARE = true;
	public static final boolean IGNORING_ELEMENT_CONTENT_WHITESPACE = true;

    /**
     *
     * @throws FileNotFoundException
     */
    @PostConstruct
	public void init() {
            
                // Inizializzazione cache dei validatori
		logInfo("Inizializzazione cache validatori {}", TipoFileEnum.XSD.name());
		this.cacheXsd = new HashMap<>();
		logInfo("Inizializzazione cache validatori {}", TipoFileEnum.SCHEMATRON.name());
		this.cacheSchematron = new HashMap<>();

		// Inizializzazione della risorsa XML-CATALOG
		this.urlFileXmlCatalog = getClass().getClassLoader().getResource(XML_CATALOG_XML);
		if (urlFileXmlCatalog == null) {
			logError(
					"Attenzione, si è verificato un errore durante il recupero del catalogo, verificare che il file {} sia presente dentro al progetto",
					XML_CATALOG_XML
			);
			throw new IllegalStateException(
					String.format(
							"File %s non trovato all'interno del progetto",
							XML_CATALOG_XML
					)
			);
		}

		// Recupera ed inizializza una tantum i parametri XSLT da utilizzare per i cataloghi
		initXsltParameters();
		
	}

	@Autowired
	private OvTipoDocumentoJpaRepository tipoDocumentoRepository;

	@Autowired
	private LocalServiceUriResolverInterface uriResolver;

	@Autowired
	private ConfigurazioneServiceInterface configurazioneService;

	@Override
	public List<OvTipoDocumento> filtraTuttiITipiDocumento() {
		return tipoDocumentoRepository.findAll();
	}

	@Override
	public OvTipoDocumento getOvTipoDocumentoById(int idTipoDocumento) {
		return tipoDocumentoRepository.findOne(idTipoDocumento);
	}

	@Override
	public OvValidatore filtraValidatore(OvTipoDocumento tipodocumento, TipoFileEnum tipoFileEnum) {

		if (tipodocumento == null) {

			logError("Impossibile recuperare un tipo documento null");
			throw new NullPointerException("Tipo documento null");
		}

		if (tipoFileEnum == null) {

			logError("Impossibile recuperare un tipo file null");
			throw new NullPointerException("Tipo file enum null");
		}

		logDebug("Recupero dei validatori associati al tipo documento {}", tipodocumento.getIdTipoDocumento());
		List<OvValidatore> validatoriSuTipodocumento = tipodocumento.getValidatori();
		for (OvValidatore validatoreCorrente : validatoriSuTipodocumento) {

			if (tipoFileEnum.equals(validatoreCorrente.getCdTipoFile())) {
				logDebug("Ritrovato il validatore {}", validatoreCorrente.getIdValidatore());
				return validatoreCorrente;
			}
		}

		logError("Validatore non trovato");
		return null;
	}

	@Override
	public ValidationReport effettuaValidazione(byte[] documento, OvTipoDocumento tipodocumento) {

		ValidationReport validationReport = new ValidationReport();

		try {

			// Esecuzione validazione XSD
			logInfo("Avvio validazione XSD");
			OvValidatore validatoreXsd = filtraValidatore(tipodocumento, TipoFileEnum.XSD);
			validazioneXsd(
					documento,
					getSchemaXsd(validatoreXsd)
			);
			logInfo("Validazione XSD completata con successo");
		} catch (SAXException | ParserConfigurationException | IOException e) {

			// Logging dell'errore e restituzione del report contenente solo l'errore XSD
			logError("Si è verificato un errore in sede di validazione XSD: {}", e.getMessage(), e);
			validationReport.setDescrizioneErroreXsd(
					e.getMessage()
			);
			return validationReport;
		}

		try {

			// Esecuzione validazione schematron
			logInfo("Avvio validazione XSLT");
			OvValidatore validatoreSchematron = filtraValidatore(tipodocumento, TipoFileEnum.SCHEMATRON);
			Collection<ValidationAssert> validationAsserts = validazioneSemantica(
					new String(documento, StandardCharsets.UTF_8),
					getSchematron(validatoreSchematron)
			);

			// Aggiungo ogni eventuale errore di validazione
			validationAsserts.forEach(validationReport::aggiungiDettaglio);
			logInfo(
					"Validazione XSLT completata {} errori",
					CollectionUtils.isEmpty(validationAsserts) ?
							"senza"
							: "con"
			);
		} catch (Exception e) {

			// Logging dell'errore
			logError("Si è verificato un errore durante la validazione semantica: {}", e.getMessage(), e);
		}

		return validationReport;
	}

	/**
	 * Effettua la validzione XSD di un dato documento.
	 *
	 * @param documentoXml è l'xml di riferimento
	 * @param schema       è lo schema XSD di riferimento
	 */
	private void validazioneXsd(byte[] documentoXml, Schema schema)
			throws SAXException, ParserConfigurationException, IOException {

		logInfo("Avvio validazione XSD del documento");
		logDebug("Costruzione istanza {}", DocumentBuilderFactory.class.getName());
		DocumentBuilderFactory lFactory = DocumentBuilderFactory.newInstance();

		logDebug("Avvio configurazione delle impostazioni del validatore XSD");

		/*
		 * Disabilitazione delle features XXE per impedire un possibile attacco con XML
		 * Injection.
		 */
		lFactory.setFeature(OWASP_FEATURES_TO_DISALLOW_XXE_ENT, OWASP_FEATURES_TO_DISALLOW_XXE_ENT_VALUE);
		logDebug(LOGGING_KEY_VALUE, OWASP_FEATURES_TO_DISALLOW_XXE_ENT, OWASP_FEATURES_TO_DISALLOW_XXE_ENT_VALUE);
		lFactory.setFeature(OWASP_FEATURES_TO_DISALLOW_XXE_PAR, OWASP_FEATURES_TO_DISALLOW_XXE_PAR_VALUE);
		logDebug(LOGGING_KEY_VALUE, OWASP_FEATURES_TO_DISALLOW_XXE_PAR, OWASP_FEATURES_TO_DISALLOW_XXE_PAR_VALUE);
		lFactory.setFeature(OWASP_FEATURES_TO_DISALLOW_DOCTYPE, OWASP_FEATURES_TO_DISALLOW_DOCTYPE_VALUE);
		logDebug(LOGGING_KEY_VALUE, OWASP_FEATURES_TO_DISALLOW_DOCTYPE, OWASP_FEATURES_TO_DISALLOW_DOCTYPE_VALUE);

		// Configurazione per la validazione XSD
		lFactory.setNamespaceAware(NAMESPACE_AWARE);
		logDebug(LOGGING_KEY_VALUE, "namespaceAware", NAMESPACE_AWARE);
		lFactory.setIgnoringElementContentWhitespace(IGNORING_ELEMENT_CONTENT_WHITESPACE);
		logDebug(LOGGING_KEY_VALUE, "ignoringElementContentWhitespace", IGNORING_ELEMENT_CONTENT_WHITESPACE);
		lFactory.setSchema(schema);

		DocumentBuilder dBuilder = lFactory.newDocumentBuilder();
		setDefaultErrorHandler(dBuilder);

		// Applico la validazione XSD al documento
		logDebug("Avvio della validazione xsd");
		dBuilder.parse(
				IOUtils.toInputStream(
						new String(
								documentoXml,
								StandardCharsets.UTF_8
						)
				)
		);
		logDebug("Validazione xsd conclusa");
	}

	/**
	 * Recupera un validatore XSD.
	 *
	 * @param validatoreXsd è l'entry della tabella "Validatore"
	 * @return lo Schema XSD di riferimento
	 * @throws SAXException nel caso in cui si verifichino problemi di compilazione dell'XSD
	 */
	private Schema getSchemaXsd(OvValidatore validatoreXsd) throws SAXException {

		// Provo di recuperare l'xsd dalla cache
		Schema schemaXsd = cacheXsd.get(validatoreXsd.getIdValidatore());
		if (schemaXsd == null) {

			return createAndCacheSchema(validatoreXsd);
		}

		return schemaXsd;
	}

	/**
	 * Recupera e crea uno schema XSD (utilizzato se lo schema interessato non compare nella cache.
	 *
	 * @param validatoreXsd è la riga del database corrispondente al validatore in questione
	 * @return lo schema XSD
	 * @throws SAXException nel caso in cui qualcosa vada storto durante il recupero
	 */
	private Schema createAndCacheSchema(OvValidatore validatoreXsd) throws SAXException {

		// Se non ho l'xsd nella cache, lo istanzio e lo inserisco in cache
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		schemaFactory.setResourceResolver(
				new com.sun.org.apache.xerces.internal.util.XMLCatalogResolver(
						new String[]{urlFileXmlCatalog.toExternalForm()}
				)
		);

		logInfo("Caricamento schema [Validatore={}]", validatoreXsd.getIdValidatore());
		Schema schemaXsd = schemaFactory.newSchema(
				new StreamSource(
						new StringReader(
								new String(
										validatoreXsd.getBlFile(),
										StandardCharsets.UTF_8
								)
						)
				)
		);
		cacheXsd.put(validatoreXsd.getIdValidatore(), schemaXsd);

		logInfo("Schema [Validatore={}] caricato in cache", validatoreXsd.getIdValidatore());
		return schemaXsd;
	}

	/**
	 * Imposta l'ErrorHandler sulla DocumentBuilder di riferimento.
	 *
	 * @param dBuilder è l'oggetto DocumentBuilder responsabile dell'applicazione della validazione
	 */
	private void setDefaultErrorHandler(DocumentBuilder dBuilder) {
		dBuilder.setErrorHandler(new ErrorHandler() {

			@Override
			public void warning(SAXParseException ex) {
				logWarn("Validazione XSD superata con il seguente warning: {}", ex.getMessage(), ex);
			}

			@Override
			public void fatalError(SAXParseException ex) throws SAXException {
				logError("Validazione XSD del documento fallita: {}", ex.getMessage(), ex);
				throw ex;
			}

			@Override
			public void error(SAXParseException ex) throws SAXException {
				logError("Validazione XSD del documento fallita: {}", ex.getMessage(), ex);
				throw ex;
			}
		});
	}

	/**
	 * Effettua la validazione semantica (XSL, Schematron) su un documento XML dato (codificato in UTF-8).
	 *
	 * @param documentoXml è il documento XML da validare
	 * @param xslt         è il template schematron da utilizzare
	 * @return l'elenco di regole violate
	 */
	public Collection<ValidationAssert> validazioneSemantica(String documentoXml, Templates xslt) {
		Vector<ValidationAssert> vectorResult = new Vector<>();

		logDebug("Eseguo validazione schematron");

		try {
			Transformer transformer = xslt.newTransformer();
			buildTransformer(transformer);
			StringWriter stringWriter = new StringWriter();
			StreamResult streamResult = new StreamResult(stringWriter);

			StringReader stringReader = new StringReader(documentoXml);
			StreamSource streamSource = new StreamSource(stringReader);

			transformer.transform(streamSource, streamResult);

			StringReader stringReader2 = new StringReader(stringWriter.getBuffer().toString());

			JAXBContext jaxbContext = JAXBContext.newInstance(SchematronOutput.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			SchematronOutput schematron = (SchematronOutput) jaxbUnmarshaller.unmarshal(stringReader2);

			List<?> l = schematron.getActivePatternAndFiredRuleAndFailedAssert();

			for (Object type : l) {
				if (type instanceof FailedAssert) {
					FailedAssert f = (FailedAssert) type;
					vectorResult.add(
							new ValidationAssert(f.getTest(), f.getLocation(), f.getText(), f.getFlag())
					);
					logInfo(
							"Errore di validazione semantica: [test=\"{}\", posizione={}, descrizione=\"{}\", livello={}]",
							f.getTest(), f.getLocation(), f.getText(), f.getFlag()
					);
				}

			}

		} catch (TransformerException | JAXBException e) {
			logError("Si è verificato un errore durante l'applicazione della validazione schematron: {}", e.getMessage(), e);
			throw new IllegalStateException("ERRORE VALIDAZIONE SCHEMATRON DOCUMENTO:", e);
		}

		return vectorResult;
	}

	/**
	 * Dato un validatore, se ne recupera lo schematron.
	 *
	 * @param validatore è l'entità database che rappresenta il validatore
	 * @return il valiatore schematron
	 * @throws TransformerConfigurationException se qualcosa va storto durante il recupero
	 */
	private Templates getSchematron(OvValidatore validatore) throws TransformerConfigurationException {

		Templates schematronTemplates = cacheSchematron.get(validatore.getIdValidatore());

		if (schematronTemplates == null) {
			schematronTemplates = createAndCacheSchematronTemplate(validatore);
		}

		return schematronTemplates;
	}

	/**
	 * Inizializza e inserisce in cache un validatore schematron.
	 *
	 * @param validatore è l'oggetto responsabile della validazione
	 * @return il template XSLT
	 * @throws TransformerConfigurationException nel caso in cui qualcosa vada storto in fase di configurazione
	 */
	private Templates createAndCacheSchematronTemplate(OvValidatore validatore) throws TransformerConfigurationException {
		String blobSchematron = new String(validatore.getBlFile());

		StreamSource schematronSource = new StreamSource(new StringReader(blobSchematron));
		TransformerFactory schematronFactory = TransformerFactory.newInstance(ValidatorServiceInterface.NET_SF_SAXON_TRANSFORMER_FACTORY_IMPL, null);

		Templates schematronTemplates = schematronFactory.newTemplates(schematronSource);
		cacheSchematron.put(validatore.getIdValidatore(), schematronTemplates);
		return schematronTemplates;
	}

	/**
	 * Completa la configurazione del transformer XSL.
	 *
	 * @param transformer è il transformer che deve essere configurato
	 */
	private void buildTransformer(Transformer transformer) {

		// Imposto l'URI resolver
		transformer.setURIResolver(uriResolver);

		// Configuro tutti i parametri XSLT previsti
		Set<String> keySet = xsltParameters.keySet();
		for (String key : keySet) {

			// Recupero il parametro e lo configuro nel transformer
			String xsltParam = xsltParameters.get(key);
			logDebug("Imposto parametro XSLT [{}=\"{}\"]", key, xsltParam);
			transformer.setParameter(key, xsltParameters.get(key));
		}

	}

	/**
	 * Recupera l'URL dove è reso disponibile il catalogo specifico.
	 *
	 * @param nomeCatalog è il riferimento del catalogo
	 * @param versione    è la versione del catalogo
	 * @return l'URL del catalogo
	 */
	private String getUrlForCatalog(String nomeCatalog, String versione) {
		String contextPath = configurazioneService.readValue(ChiaveConfigurazioneEnum.CONTEXT_PATH);
		return contextPath + "/" + CATALOG_BASE_URL + "?nomeCatalog=" + nomeCatalog + "&versione=" + versione;
	}

	/**
	 * Inizializza la mappa di parametri XSLT da configurare sul Transformer.
	 */
	private void initXsltParameters() {

		// Recupero tutti i cataloghi
		List<OvCatalog> cataloghi = catalogJpaRepository.findAll();

		// Mi assicuro che la lista dei cataloghi non sia vuota
		if (CollectionUtils.isEmpty(cataloghi)) {

			// Logging dell'errore
			logError("Nessun catalogo presente all'interno del sistema, verificare la configurazione");
			throw new IllegalStateException(
					"OV_CATALOG table is empty"
			);
		}

		// Verifico che tutti i cataloghi abbiano configurato il codice del parametro XSL
		boolean foundCatalogNoXsltCode = false;
		for (OvCatalog ovCatalog : cataloghi) {
			if (StringUtils.isEmpty(ovCatalog.getCdParametroXsl())) {
				logWarn("Il catalogo {} (id={}) non definisce un parametro XSLT", ovCatalog.getNmNome(), ovCatalog.getIdCatalog());
				foundCatalogNoXsltCode = true;
			}
		}
		if (foundCatalogNoXsltCode) {
			logError("Attenzione, uno o più cataloghi non presentano la definizione del campo OV_CATALOG.CD_PARAMETRO_XSL, verificare la configurazione");
			throw new IllegalStateException(
					"OV_CATALOG table is inconsistent"
			);
		}

		// Imposto i parametri XSLT
		this.xsltParameters = new HashMap<>();
		logInfo("Impostazione dei parametri XSLT");
		cataloghi.forEach(ovCatalog -> this.xsltParameters.put(
				ovCatalog.getCdParametroXsl(),
				getUrlForCatalog(
						ovCatalog.getNmNome().name(),
						ovCatalog.getCdVersione()
				)
		));
	}

}


