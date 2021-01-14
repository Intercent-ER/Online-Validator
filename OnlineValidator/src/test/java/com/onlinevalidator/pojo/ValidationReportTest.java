package com.onlinevalidator.pojo;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Manuel Gozzi
 */
public class ValidationReportTest {

	@Test
	public void contieneErrori() {

		ValidationReport validationReport = new ValidationReport();
		ValidationAssert validationAssert = new ValidationAssert("test", "location", "testo", "fatal");

		validationReport.aggiungiDettaglio(validationAssert);

		assertTrue(validationReport.contieneErrori());

		validationReport = new ValidationReport();
		assertFalse(validationReport.contieneErrori());

	}

	@Test
	public void aggiungiDettaglio() {

		ValidationReport validationReport = new ValidationReport();
		assertTrue(validationReport.getErroriDiValidazione() != null && validationReport.getErroriDiValidazione().isEmpty());

		ValidationAssert validationAssert = new ValidationAssert("test", "location", "testo", "fatal");
		validationReport.aggiungiDettaglio(validationAssert);

		assertNotNull(validationReport.getErroriDiValidazione());

	}

	@Test
	public void testAggiungiDettaglio() {

		ValidationReport validationReport = new ValidationReport();
		assertNull(validationReport.getDescrizioneErroreXsd());

		validationReport.aggiungiDettaglio("descrizione errore xsd");
		assertNotNull(validationReport.getDescrizioneErroreXsd());
		assertEquals("descrizione errore xsd", validationReport.getDescrizioneErroreXsd());

	}

	@Test
	public void getErroriDiValidazione() {

		ValidationReport validationReport = new ValidationReport();
		ValidationAssert validationAssert = new ValidationAssert("test", "location", "testo", "fatal");
		validationReport.aggiungiDettaglio(validationAssert);

		List<ValidationAssert> lista1 = validationReport.getErroriDiValidazione();
		List<ValidationAssert> lista2 = validationReport.getErroriDiValidazione();

		assertSame(lista1, lista2);

	}
}