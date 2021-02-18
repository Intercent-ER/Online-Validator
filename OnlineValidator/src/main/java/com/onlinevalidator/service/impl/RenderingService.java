package com.onlinevalidator.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.onlinevalidator.dto.Render;
import com.onlinevalidator.dto.ValidationAssert;
import com.onlinevalidator.dto.ValidationReport;
import com.onlinevalidator.exception.rendering.PdfRenderingException;
import com.onlinevalidator.exception.rendering.RenderingException;
import com.onlinevalidator.exception.rendering.XmlRenderingException;
import com.onlinevalidator.generatedsources.xsd.rendering.*;
import com.onlinevalidator.pojo.TipoRenderingEnum;
import com.onlinevalidator.service.RenderingServiceInterface;
import com.onlinevalidator.util.CostantiWeb;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Manuel Gozzi
 */
@Service
public class RenderingService implements RenderingServiceInterface {

	private static final String TITILLIUM_WEB_FONT = "/fonts/TitilliumWeb-Regular.ttf";
	private static final String TITILLIUM_WEB_FONT_BOLD = "/fonts/TitilliumWeb-Bold.ttf";
	private static final String VERSIONE_DEFAULT = "1.0";

	private BaseFont pdfMainFont, pdfMainFontBold;

	private JAXBContext context;

	@PostConstruct
	public void init() {

		try {

			// Inizializzazione dei font PDF
			logInfo("Inizializzazione font");
			this.pdfMainFont = BaseFont.createFont(TITILLIUM_WEB_FONT, BaseFont.WINANSI, true);
			this.pdfMainFontBold = BaseFont.createFont(TITILLIUM_WEB_FONT_BOLD, BaseFont.WINANSI, true);
		} catch (IOException | DocumentException e) {

			// Logging dell'errore e restituzione dell'errore
			logError("Si è verificato un errore durante l'impostazione del font per il rendering PDF: {}", e.getMessage(), e);
			throw new IllegalStateException(e);
		}

		try {

			// Costruzione del contesto JAXB
			logInfo("Inizializzazione contesto JAXB");
			this.context = JAXBContext.newInstance(RapportoValidazione.class);
		} catch (JAXBException e) {

			// Logging dell'errore e restituzione dell'errore
			logError("Si è verificato un errore durante la costruzione del contesto JAXB: {}", e.getMessage(), e);
			throw new IllegalStateException(e);
		}
	}

	@Override
	public Render render(ValidationReport validationReport, TipoRenderingEnum tipoRenderingEnum) throws RenderingException {

		if (tipoRenderingEnum == null) {

			logError("TipoRenderingEnum è null");
			throw new NullPointerException("Impossibile eseguire il rendering senza specificare il tipo");
		}

		switch (tipoRenderingEnum) {
			case PDF:
				return renderToPdf(validationReport);
			case XML:
				return renderToXml(validationReport);
			default:
				throw new RenderingException("Impossibile eseguire il rendering per il tipoRendering "
						+ tipoRenderingEnum.name());
		}
	}

	@Override
	public Render renderToPdf(ValidationReport validationReport) throws PdfRenderingException {

		logInfo("Avvio esportazione PDF");
		if (validationReport == null) {
			throw new PdfRenderingException("Impossibile eseguire il rendering PDF: null");
		}

		// Il tipo di rendering è sempre PDF, in questo caso
		TipoRenderingEnum tipoRenderingEnum = TipoRenderingEnum.PDF;

		try (
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
		) {

			// Preparo ed apro il documento PDF
			logDebug("Apertura del nuovo documento PDF");
			Document pdfDocument = new Document();
			PdfWriter.getInstance(pdfDocument, byteArrayOutputStream);
			pdfDocument.open();

			// Compongo la struttura del pdf
			logDebug("Avvio scrittura del documento PDF");
			composePdfFile(pdfDocument, validationReport);

			// Aggiungo in conclusione il file validato
			pdfDocument.newPage();
			pdfDocument.add(new Paragraph("Documento:", new Font(pdfMainFont, 19f, Font.NORMAL, BaseColor.BLACK)));
			logDebug("Aggiungo documento validato");
			pdfDocument.add(
					Chunk.NEWLINE
			);
			pdfDocument.add(
					new Paragraph(
							validationReport.getDocumentoValidato().replace("\t", "    "),
							new Font(pdfMainFont, 9f, Font.NORMAL, BaseColor.DARK_GRAY)
					)
			);

			logDebug("Chiusura del documento PDF");
			pdfDocument.close();

			logDebug("Predisposizione del Render finale");
			return new Render(
					byteArrayOutputStream.toByteArray(),
					tipoRenderingEnum,
					getFileName(tipoRenderingEnum)
			);

		} catch (IOException | DocumentException e) {
			logError("Si è verificato un errore durante il rendering del file PDF: {}", e.getMessage(), e);
			throw new PdfRenderingException(e);
		}

	}

	@Override
	public Render renderToXml(ValidationReport validationReport) throws XmlRenderingException {

		if (validationReport == null) {
			throw new XmlRenderingException("Impossibile eseguire il rendering XML: null");
		}

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

			TipoRenderingEnum xml = TipoRenderingEnum.XML;

			RapportoValidazione rapportoValidazione = convertiInRapportoValidazione(validationReport);
			Marshaller marshaller = context.createMarshaller();

			// Auto indentazione del risultato XML
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(rapportoValidazione, byteArrayOutputStream);

			return new Render(
					byteArrayOutputStream.toByteArray(),
					xml,
					getFileName(xml)
			);

		} catch (Exception e) {

			logError("Si è verificato un errore in fase di rendering XML: {}", e.getMessage(), e);
			throw new XmlRenderingException(e);
		}
	}

	private RapportoValidazione convertiInRapportoValidazione(ValidationReport validationReport) {
		RapportoValidazione rapportoValidazione = new RapportoValidazione();
		rapportoValidazione.setVersione(VERSIONE_DEFAULT);
		rapportoValidazione.setDataValidazione(
				new SimpleDateFormat(CostantiWeb.PATTERN_SIMPLE_DATE_FORMAT).format(validationReport.getDataDiGenerazione())
		);

		if (!StringUtils.isEmpty(validationReport.getDescrizioneErroreXsd())) {

			rapportoValidazione.setErroreXSD(validationReport.getDescrizioneErroreXsd());
			rapportoValidazione.setEsito(EsitoValidazione.FATAL);
		} else {

			composeListFailedAssert(validationReport, rapportoValidazione);
		}

		return rapportoValidazione;
	}

	/**
	 * Compone una lista di failed assert, sull'oggetto Java JAXB.
	 *
	 * @param validationReport    è il report di validazione
	 * @param rapportoValidazione è la rappresentazione JAXB dell'XSD di riferimento
	 */
	private void composeListFailedAssert(ValidationReport validationReport, RapportoValidazione rapportoValidazione) {
		if (validationReport.contieneErrori()) {

			ListaAssertValidazioneType listaAssertValidazioneType = new ListaAssertValidazioneType();

			boolean contieneFatal = false;
			for (ValidationAssert validationAssert : validationReport.getErroriDiValidazione()) {

				AssertValidazioneType assertValidazioneType = new AssertValidazioneType();
				assertValidazioneType.setDescrizione(validationAssert.getTesto());
				assertValidazioneType.setPosizione(validationAssert.getLocation());
				assertValidazioneType.setTest(validationAssert.getTest());


				if (validationAssert.isFatal()) {
					contieneFatal = true;
					assertValidazioneType.setLivelloErrore(Livello.FATAL);
				} else {
					assertValidazioneType.setLivelloErrore(Livello.WARNING);
				}

				listaAssertValidazioneType.getAssertValidazione().add(assertValidazioneType);
			}

			rapportoValidazione.setListaAssertValidazione(listaAssertValidazioneType);

			rapportoValidazione.setEsito(
					contieneFatal ?
							EsitoValidazione.FATAL
							: EsitoValidazione.WARNING
			);

		} else {

			rapportoValidazione.setEsito(EsitoValidazione.OK);
		}
	}

	/**
	 * Funzione che si occupa di comporre interamente il documento PDF, che contiene il risultato di validazione.
	 *
	 * @param pdfDocument      è il documento PDF in via di composizione
	 * @param validationReport è il report di validazione di cui occorre effettuare il rendering
	 * @throws DocumentException nel caso in cui qualcosa vada storto durante il rendering
	 */
	private void composePdfFile(Document pdfDocument, ValidationReport validationReport) throws DocumentException {

		// Titolo principale
		pdfDocument.add(
				new Paragraph(
						"Rapporto di validazione",
						new Font(pdfMainFontBold, 23f, Font.NORMAL, BaseColor.BLACK)
				)
		);

		// Aggiungo la data di generazione del PDF
		pdfDocument.add(
				new Paragraph(
						"Report di validazione generato in data " + new SimpleDateFormat(CostantiWeb.PATTERN_SIMPLE_DATE_FORMAT).format(validationReport.getDataDiGenerazione()),
						new Font(pdfMainFont, 12f, Font.NORMAL, BaseColor.BLACK)
				)
		);

		// Aggiungo la versione dello schematron
		if (!StringUtils.isEmpty(validationReport.getVersioneSchematron())) {
			pdfDocument.add(
					new Paragraph(
							String.format(
									"Versione schematron: %s",
									validationReport.getVersioneSchematron()
							),
							new Font(pdfMainFont, 12f, Font.NORMAL, BaseColor.DARK_GRAY)
					)
			);
		}

		if (validationReport.isValido()) {

			// Descrivo il risultato positivo della validazione
			pdfDocument.add(
					new Paragraph(
							"Risultato: il file è valido",
							new Font(pdfMainFont, 19f, Font.NORMAL, getDarkGreenColor())
					)
			);
		} else {

			// Descrivo lo status negativo della validazione
			pdfDocument.add(
					new Paragraph(
							"Risultato: il file non è valido",
							new Font(pdfMainFont, 19f, Font.NORMAL, BaseColor.RED)
					)
			);
			pdfDocument.add(
					Chunk.NEWLINE
			);
		}

		if (!StringUtils.isEmpty(validationReport.getDescrizioneErroreXsd())) {

			writeDetail(pdfDocument, "Descrizione errore XSD:", validationReport.getDescrizioneErroreXsd(), "Aggiungo descrizione errore XSD [{}]", BaseColor.RED);
		}

		if (validationReport.contieneErrori()) {

			// Scrivo i dettagli di validazione
			writePdfDetails(pdfDocument, validationReport);
		}

	}

	/**
	 * Restituisce il colore "darkGreen" codificato in valori RGB.
	 *
	 * @return il {@link BaseColor} corrispondente al darkGreen
	 */
	private BaseColor getDarkGreenColor() {
		return new BaseColor(1, 100, 32);
	}

	/**
	 * Funzione che si occupa di comporre i paragrafi che dettagliano il contenuto del pdf.
	 *
	 * @param pdfDocument      è il documento pdf in via di costruzione
	 * @param validationReport è il report di validazione di cui occorre eseguire il rendering
	 * @throws DocumentException nel caso in cui qualcosa vada storto
	 */
	private void writePdfDetails(Document pdfDocument, ValidationReport validationReport) throws DocumentException {

		if (!StringUtils.isEmpty(validationReport.getDescrizioneErroreXsd())) {

			writeDetail(pdfDocument, "Errore XSD:", validationReport.getDescrizioneErroreXsd(), "Aggiungo errore XSD [{}]", BaseColor.RED);
		} else {

			for (ValidationAssert validationAssert : validationReport.getErroriDiValidazione()) {

				writeDetail(pdfDocument, "Test:", validationAssert.getTest(), "Aggiungo test [{}]", BaseColor.BLACK);

				writeDetail(pdfDocument, "Posizione:", validationAssert.getLocation(), "Aggiungo posizione [{}]", BaseColor.BLACK);

				writeDetail(pdfDocument, "Livello:", validationAssert.isWarning() ?
						"WARNING"
						: "FATAL", "Aggiungo level [{}]", validationAssert.isWarning() ? BaseColor.ORANGE
						: BaseColor.RED);

				writeDetail(
						pdfDocument,
						"Descrizione:",
						validationAssert.getTesto(),
						"Aggiungo descrizione [{}]",
						validationAssert.isWarning() ? BaseColor.ORANGE
								: BaseColor.RED
				);

				pdfDocument.add(
						Chunk.NEWLINE
				);
			}
		}

	}

	/**
	 * Scrive un dettaglio all'interno del documento PDF.
	 *
	 * @param pdfDocument           è il documento pdf in via di costruzione
	 * @param intestazioneParagrafo è l'intestazione del paragrafo
	 * @param contenutoParagrafo    è il contenuto del paragrafo
	 * @param messaggioDiLog        è il messaggio di log stampato a livello DEBUG
	 * @param coloreDelTesto        è il colore del testo renderizzato
	 * @throws DocumentException nel caso in cui qualcosa vada storto durante il rendering
	 */
	private void writeDetail(Document pdfDocument, String intestazioneParagrafo, String contenutoParagrafo, String messaggioDiLog,
							 BaseColor coloreDelTesto) throws DocumentException {
		pdfDocument.add(new Paragraph(
				intestazioneParagrafo,
				new Font(pdfMainFontBold, 11f, Font.NORMAL, BaseColor.BLACK)
		));
		Paragraph test = writeParagraph(contenutoParagrafo, messaggioDiLog, coloreDelTesto);
		pdfDocument.add(test);
	}

	/**
	 * Funzione che si occupa di scrivere un paragrafo all'interno del documento PDF.
	 *
	 * @param valoreDaInserire  è il contenuto del paragrafo
	 * @param messaggioLogDebug è il messaggio di log stampato a livello DEBUG
	 * @param colore            è il colore
	 * @return il paragrafo
	 */
	private Paragraph writeParagraph(String valoreDaInserire, String messaggioLogDebug, BaseColor colore) {
		logDebug(messaggioLogDebug, valoreDaInserire);
		return new Paragraph(
				valoreDaInserire,
				new Font(pdfMainFont, 11f, Font.NORMAL, colore)
		);
	}

	/**
	 * Costruisce il nome di un file di esportazione partendo dal formato di riferimento.
	 *
	 * @param tipoRenderingEnum è la tipologia di rendering che si sta effettuando
	 * @return il nome del file comprensivo di estensione
	 */
	private String getFileName(TipoRenderingEnum tipoRenderingEnum) {
		String name = new SimpleDateFormat("dd-MM-yyyy").format(new Date())
				+ "-ReportValidazione";
		switch (tipoRenderingEnum) {
			case PDF:
				return name + ".pdf";
			case XML:
				return name + ".xml";
			default:
				return name + ".txt";
		}
	}
}
