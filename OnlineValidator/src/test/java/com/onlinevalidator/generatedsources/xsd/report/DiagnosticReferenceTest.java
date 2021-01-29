package com.onlinevalidator.generatedsources.xsd.report;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * @author Manuel Gozzi
 */
public class DiagnosticReferenceTest {

	@Test
	public void getText() {

		assertNull(new DiagnosticReference().getText());
	}

	@Test
	public void setText() {

		new DiagnosticReference().setText("text");
	}

	@Test
	public void getDiagnostic() {

		assertNull(new DiagnosticReference().getDiagnostic());
	}

	@Test
	public void setDiagnostic() {

		new DiagnosticReference().setDiagnostic("diagnostic");
	}
}