package com.onlinevalidator.service.impl;

import com.onlinevalidator.generatedsources.xsd.FailedAssert;
import com.onlinevalidator.generatedsources.xsd.SchematronOutput;
import com.onlinevalidator.model.Catalog;
import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.model.Validatore;
import com.onlinevalidator.model.enumerator.TipoFileEnum;
import com.onlinevalidator.pojo.ValidationAssert;
import com.onlinevalidator.pojo.ValidationReport;
import com.onlinevalidator.repository.CatalogJpaRepository;
import com.onlinevalidator.repository.TipoDocumentoJpaRepositoryInterface;
import com.onlinevalidator.service.LocalServiceUriResolverInterface;
import com.onlinevalidator.service.ValidatorServiceInterface;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ValidatorService implements ValidatorServiceInterface {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorService.class);
	private static final String XML_CATALOG_XML = "xml-catalog.xml";

	// Mappa che contiene i validatori XSD gestiti in cache (chiave: Validatore.id; valore: Schema)
	private Map<Integer, Schema> cacheXsd;

	// Mappa che contiene i validatori SCH gestiti in cache (chiave: Validatore.id; valore: Templates)
	private Map<Integer, Templates> cacheSchematron;

	@PostConstruct
	public void init() {
		this.cacheXsd = new HashMap<>();
		this.cacheSchematron = new HashMap<>();
	}

	@Autowired
	private TipoDocumentoJpaRepositoryInterface tipoDocumentoRepository;

	@Autowired
	private CatalogJpaRepository catalogJpaRepository;

	@Autowired
	private LocalServiceUriResolverInterface uriResolver;

	@Override
	public Tipodocumento getEntity(int id) {
		return tipoDocumentoRepository.findOne(id);
	}

	@Override
	public Tipodocumento getValidatoreByTipoDocumento(int idTipoDocumento) {
		return null;
	}

	@Override
	public List<Tipodocumento> getAllEntity() {
		return tipoDocumentoRepository.findAll();
	}

	@Override
	public Tipodocumento getTipodocumentoById(int idTipoDocumento) {
		return tipoDocumentoRepository.findOne(idTipoDocumento);
	}

	@Override
	public Validatore filtraValidatore(Tipodocumento tipodocumento, TipoFileEnum tipoFileEnum) {

		if (tipodocumento == null) {

			logger.error("Attenzione, invocazione del metodo sbagliata");

			throw new IllegalStateException("Errore 1");
		}
		if (tipoFileEnum == null) {

			throw new IllegalStateException("Errore 2");
		}
		List<Validatore> validatoriSuTipodocumento = tipodocumento.getValidatori();
		for (Validatore validatoreCorrente : validatoriSuTipodocumento) {
			if (tipoFileEnum.equals(validatoreCorrente.getTipoFileEnum())) {

				return validatoreCorrente;
			}
		}
		logger.error("Validatore non trovato");
		return null;
	}

	@Override
	public ValidationReport effettuaValidazione(byte[] documento, Tipodocumento tipodocumento) {

		ValidationReport validationReport = new ValidationReport();

		try {

			// Esecuzione validazione XSD
			Validatore validatoreXsd = filtraValidatore(tipodocumento, TipoFileEnum.XSD);
			validazioneXsd(
					documento,
					getSchemaXsd(validatoreXsd)
			);
		} catch (SAXException | ParserConfigurationException | IOException e) {

			// Logging dell'errore e restituzione del report contenente solo l'errore XSD
			logger.error("Si è verificato un errore in sede di validazione XSD: {}", e.getMessage(), e);
			validationReport.aggiungiDettaglio(
					e.getMessage()
			);
			return validationReport;
		}

		try {

			// Esecuzione validazione schematron
			Validatore validatoreSchematron = filtraValidatore(tipodocumento, TipoFileEnum.SCHEMATRON);
			Collection<ValidationAssert> validationAsserts = validazioneSemantica(
					new String(documento, StandardCharsets.UTF_8),
					getSchematron(validatoreSchematron)
			);

			// Aggiungo ogni eventuale errore di validazione
			validationAsserts.forEach(validationReport::aggiungiDettaglio);
		} catch (Exception e) {

			// Logging dell'errore
			logger.error("Si è verificato un errore durante la validazione semantica: {}", e.getMessage(), e);
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

		logger.info("Avvio validazione XSD del documento");
		DocumentBuilderFactory lFactory = DocumentBuilderFactory.newInstance();

		logger.info("Configurazione delle impostazioni del validatore XSD");
		/*
		 * Disallow delle features XXE per impedire un possibile attacco con XML
		 * Injection.
		 */
		lFactory.setFeature(OWASP_FEATURES_TO_DISALLOW_XXE_ENT, false);
		lFactory.setFeature(OWASP_FEATURES_TO_DISALLOW_XXE_PAR, false);
		lFactory.setFeature(OWASP_FEATURES_TO_DISALLOW_DOCTYPE, true);

		// Configurazione per la validazione XSD
		lFactory.setNamespaceAware(true);
		lFactory.setIgnoringElementContentWhitespace(true);
		lFactory.setSchema(schema);

		DocumentBuilder dBuilder = lFactory.newDocumentBuilder();
		setDefaultErrorHandler(dBuilder);

		// Applico la validazione XSD al documento
		logger.info("Validazione in corso...");
		dBuilder.parse(
				IOUtils.toInputStream(
						new String(
								documentoXml,
								StandardCharsets.UTF_8
						)
				)
		);
	}

	/**
	 * Recupera un validatore XSD.
	 *
	 * @param validatoreXsd è l'entry della tabella "Validatore"
	 * @return lo Schema XSD di riferimento
	 * @throws SAXException nel caso in cui si verifichino problemi di compilazione dell'XSD
	 */
	private Schema getSchemaXsd(Validatore validatoreXsd) throws SAXException {

		// Provo di recuperare l'xsd dalla cache
		Schema schemaXsd = cacheXsd.get(validatoreXsd.getId());

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
	private Schema createAndCacheSchema(Validatore validatoreXsd) throws SAXException {

		// Se non ho l'xsd nella cache, lo istanzio e lo inserisco in cache
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		URL urlRisorsa = getClass().getClassLoader().getResource(XML_CATALOG_XML);
		if (urlRisorsa == null) {
			logger.error(
					"Attenzione, si è verificato un errore durante il recupero del catalogo, verificare che il file {} sia presente dentro al progetto",
					XML_CATALOG_XML
			);
			throw new SAXException();
		}

		String catalogPath = urlRisorsa.toExternalForm();
		schemaFactory.setResourceResolver(new com.sun.org.apache.xerces.internal.util.XMLCatalogResolver(new String[]{catalogPath}));

		logger.info("Caricamento Schema [Validatore={}]", validatoreXsd.getId());
		String blobXsd = new String(
				validatoreXsd.getFile()
		);

		Schema schemaXsd = schemaFactory.newSchema(
				new StreamSource(
						new StringReader(blobXsd)
				)
		);

		cacheXsd.put(validatoreXsd.getId(), schemaXsd);
		logger.info("Schema [Validatore={}] caricato in cache", validatoreXsd.getId());
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
				logger.warn("Validazione XSD superata con il seguente warning: {}", ex.getMessage(), ex);
			}

			@Override
			public void fatalError(SAXParseException ex) throws SAXException {
				logger.error("Validazionee XSD del documento fallita: {}", ex.getMessage(), ex);
				throw ex;
			}

			@Override
			public void error(SAXParseException ex) throws SAXException {
				logger.error("Validazionee XSD del documento fallita: {}", ex.getMessage(), ex);
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

		logger.info("Eseguo validazione schematron");

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
					logger.info(
							"Errore di validazione semantica: [test=\"{}\", posizione={}, descrizione=\"{}\", livello={}]",
							f.getTest(), f.getLocation(), f.getText(), f.getFlag()
					);
				}

			}

		} catch (TransformerException | JAXBException e) {
			logger.error("ERRORE VALIDAZIONE SCHEMATRON!", e);
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
	private Templates getSchematron(Validatore validatore) throws TransformerConfigurationException {

		Templates schematronTemplates = cacheSchematron.get(validatore.getId());

		if (schematronTemplates == null) {
			schematronTemplates = createAndCacheSchematronTemplate(validatore);
		}

		return schematronTemplates;
	}


	private Templates createAndCacheSchematronTemplate(Validatore validatore) throws TransformerConfigurationException {
		Templates schematronTemplates;
		String blobSchematron = new String(validatore.getFile());

		StreamSource schematronSource = new StreamSource(new StringReader(blobSchematron));
		TransformerFactory schematronFactory = TransformerFactory.newInstance();
		// Sopprima il warning relativo alla vesione dell'XSTL
		// processor
		schematronFactory.setAttribute("http://saxon.sf.net/feature/version-warning", Boolean.FALSE);

		schematronTemplates = schematronFactory.newTemplates(schematronSource);
		cacheSchematron.put(validatore.getId(), schematronTemplates);
		return schematronTemplates;
	}

	private void buildTransformer(Transformer transformer) {
		transformer.setURIResolver(uriResolver);
		addAllParameters(transformer);
	}

	private void addAllParameters(Transformer transformer) {
		List<Catalog> listaDeiCataloghiCompleta = catalogJpaRepository.findAll();
		for (Catalog catalogo : listaDeiCataloghiCompleta) {
			transformer.setParameter(catalogo.getNome().name(), catalogo.getUrl());
		}
		// TODO completare questa parte
		/*
		Esempio:

		transformer.setParameter("xclUnitOfMeasureCode", getUrlForCatalog("UnitOfMeasureCode", "2.1"));
		transformer.setParameter("xclPaymentMeansCode", getUrlForCatalog("PaymentMeansCode", "2.1"));
		transformer.setParameter("xclFormatoAttachment", getUrlForCatalog("FormatoAttachment", "2.1"));
		transformer.setParameter("xclCategoriaImposte", getUrlForCatalog("CategoriaImposte", "2.1"));
		transformer.setParameter("xclTipoDocumento", getUrlForCatalog("TipoDocumento", "2.1"));
		transformer.setParameter("xclVATSchemes", getUrlForCatalog("VATSchemes", "2.1"));
		transformer.setParameter("xclProvinceItaliane", getUrlForCatalog("ProvinceItaliane", "1.0"));
		transformer.setParameter("xclTipoFattura", getUrlForCatalog("TipoFattura", "2.1"));
		transformer.setParameter("xclTipoParcella", getUrlForCatalog("TipoParcella", "2.1"));
		transformer.setParameter("xclOrderTypeCode", getUrlForCatalog("OrderTypeCode", "2.1"));
		transformer.setParameter("xclHandlingCode", getUrlForCatalog("HandlingCode", "2.1"));

		 */
	}

}


