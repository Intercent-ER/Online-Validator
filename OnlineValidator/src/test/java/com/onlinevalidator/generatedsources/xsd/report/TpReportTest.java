package com.onlinevalidator.generatedsources.xsd.report;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Gozzi
 */
public class TpReportTest {

	@Test
	public void getTest() {

		assertTrue(new TpReport().getTest().isEmpty());
	}

	@Test
	public void getPercorso() {

		assertTrue(new TpReport().getPercorso().isEmpty());
	}

	@Test
	public void getErrore() {

		assertTrue(new TpReport().getErrore().isEmpty());
	}
}