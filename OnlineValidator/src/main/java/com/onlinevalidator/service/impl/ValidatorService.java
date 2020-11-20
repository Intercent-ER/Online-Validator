package com.onlinevalidator.service.impl;

import com.onlinevalidator.model.TipoFileEnum;
import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.model.Validatore;
import com.onlinevalidator.pojo.ValidationAssert;
import com.onlinevalidator.pojo.ValidationReport;
import com.onlinevalidator.repository.TipoDocumentoJpaRepositoryInterface;
import com.onlinevalidator.repository.ValidatorJpaRepositoryInterface;
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

import javax.annotation.Resource;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;

@Service
public class ValidatorService implements ValidatorServiceInterface {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorService.class);

	// Mappa che contiene i validatori gestiti in cache (chiave: Validatore.id; valore: Schema)
	private Map<Integer, Schema> cacheValidatori;

	@Autowired
	private ValidatorJpaRepositoryInterface repository;

	@Resource
	private TipoDocumentoJpaRepositoryInterface tipoDocumentoRepository;

	@Resource
	private ValidatorJpaRepositoryInterface validatorRepository;

	@Autowired
	private LocalServiceUriResolverInterface uriResolver;

	@Override
	public Tipodocumento getEntity(int id) {

		return tipoDocumentoRepository.findOne(id);

	}

	@Override
	public List<Tipodocumento> getAllEntity() throws SQLException {

		return tipoDocumentoRepository.findAll();

	}

	@Override
	public Tipodocumento getTipodocumentoById(int idTipoDocumento) {
		return tipoDocumentoRepository.findOne(idTipoDocumento);
	}

	public Validatore filtraValidatore(Tipodocumento tipodocumento, TipoFileEnum tipoFileEnum) {

		// 1. controllo e verifico i parametri di ingresso
		if (tipodocumento == null) {
			logger.error("Attenzione, invocazione del metodo sbagliata");
			throw new IllegalStateException("Errore 1");
		}

		if (tipoFileEnum == null) {
			// errore 2
			throw new IllegalStateException("Errore 2");
		}

		List<Validatore> validatoriSuTipodocumento = tipodocumento.getValidatori();

		for (Validatore validatoreCorrente : validatoriSuTipodocumento) {

			if (tipoFileEnum.equals(validatoreCorrente.getTipoFileEnum())) {
				// 2. scrivo la logica che mi serve
				// 3. (opzionale) restituisco il risultato

				// validatore trovato
				return validatoreCorrente;
			}

		}

		logger.error("Validatore non trovato");
		return null;

	}

	public Validatore getXSDValidator(Tipodocumento docdavalidare) {

		Validatore val1 = docdavalidare.getValidatori().get(0);
		TipoFileEnum TIPO = val1.getTipoFileEnum();
		if (TIPO == TipoFileEnum.XSD) {
			return val1;
		} else {
			return docdavalidare.getValidatori().get(1);
		}
	}

	public Validatore getSCHEMATRONValidator(Tipodocumento docdavalidare) {
		Validatore val1 = docdavalidare.getValidatori().get(0);
		TipoFileEnum TIPO = val1.getTipoFileEnum();
		if (TIPO == TipoFileEnum.SCHEMATRON) {
			return val1;
		} else {
			return docdavalidare.getValidatori().get(1);
		}
	}


	@Override
	public ValidationReport effettuaValidazione(byte[] documento, Tipodocumento tipodocumento) {

		Validatore validatoreXsd = getXSDValidator(tipodocumento);
		try {

			// Esecuzione validazione XSD
			validazioneXsd(
					documento,
					getSchema(validatoreXsd)
			);
		} catch (SAXException | ParserConfigurationException | IOException e) {
			logger.error("Si è verificato un errore in sede di valdiazione XSD: {}", e.getMessage(), e);
			ValidationReport report = new ValidationReport();
			report.aggiungiDettaglio(
					new ValidationAssert("", "", e.getMessage(), "fatal")
			);
			return report;
		}

		Validatore validatoreSchematron = getSCHEMATRONValidator(tipodocumento);

		return null;
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
	 * Recupera un validatore XSD.
	 *
	 * @param validatoreXsd è l'entry della tabella "Validatore"
	 * @return lo Schema XSD di riferimento
	 * @throws SAXException nel caso in cui si verifichino problemi di compilazione dell'XSD
	 */
	private Schema getSchema(Validatore validatoreXsd) throws SAXException {

		// Provo di recuperare l'xsd dalla cache
		Schema schemaXsd = cacheValidatori.get(validatoreXsd.getId());

		if (schemaXsd == null) {

			// Se non ho l'xsd nella cache, lo istanzio e lo inserisco in cache
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			// TODO: impostare xml-catalog.xml
			String catalogPath = getClass().getClassLoader().getResource("xml-catalog.xml").toExternalForm();
			schemaFactory.setResourceResolver(new com.sun.org.apache.xerces.internal.util.XMLCatalogResolver(new String[]{catalogPath}));

			logger.info("Caricamento Schema [Validatore={}]", validatoreXsd.getId());
			String blobXsd = new String(
					validatoreXsd.getFile()
			);

			schemaXsd = schemaFactory.newSchema(
					new StreamSource(
							new StringReader(blobXsd)
					)
			);

			cacheValidatori.put(validatoreXsd.getId(), schemaXsd);
			logger.info("Schema [Validatore={}] caricato in cache", validatoreXsd.getId());

		}

		return schemaXsd;
	}

	public Collection<ValidationAssert> validaDocumentoSchematron(String documentoXml, Templates xslt) {
		Vector<ValidationAssert> vectorResult = new Vector<>();

		// refactor TipoValidazioneDocumento tvd = new
		// TipoValidazioneDocumento(codTipoDocumento, codVersione, codFormato,
		// "SCHEMATRON");

		try {
			Transformer transformer = xslt.newTransformer();
			buildTransformer(transformer);
			transformerBuilder.build(transformer, uriResolver);
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
			Iterator<?> it = l.iterator();

			while (it.hasNext()) {
				Object type = it.next();
				if (type instanceof FailedAssert) {
					FailedAssert f = (FailedAssert) type;
					ValidazioneSchematronResult vsr = new ValidazioneSchematronResult(f.getTest(), f.getLocation(), f.getText(), f.getFlag());
					vectorResult.add(vsr);
				}

			}

		} catch (TransformerException | JAXBException e) {
			logger.error("ERRORE VALIDAZIONE SCHEMATRON!", e);
			throw new IllegalStateException("ERRORE VALIDAZIONE SCHEMATRON DOCUMENTO:", e);
		}

		logger.info(vectorResult.isEmpty() ? "XML validato correttamente con relativo Schematron"
				: "XML validato con warning o fatal");

		return vectorResult;
	}

	private void buildTransformer(Transformer transformer) {
		transformer.setURIResolver(uriResolver);
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
	}

}


