package com.onlinevalidator.generatedsources.xsd.report;

import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Gozzi
 */
public class SuccessfulReportTest {

	@Test
	public void getDiagnosticReference() {

		assertTrue(new SuccessfulReport().getDiagnosticReference().isEmpty());
	}

	@Test
	public void getText() {

		assertNull(new SuccessfulReport().getText());
	}

	@Test
	public void setText() {

		new SuccessfulReport().setText("text");
	}

	@Test
	public void getId() {

		assertNull(new SuccessfulReport().getId());
	}

	@Test
	public void setId() {

		new SuccessfulReport().setId("id");
	}

	@Test
	public void getLocation() {

		assertNull(new SuccessfulReport().getLocation());
	}

	@Test
	public void setLocation() {

		new SuccessfulReport().setLocation("location");
	}

	@Test
	public void getTest() {

		assertNull(new SuccessfulReport().getTest());
	}

	@Test
	public void setTest() {

		new SuccessfulReport().setTest("test");
	}

	@Test
	public void getRole() {

		assertNull(new SuccessfulReport().getRole());
	}

	@Test
	public void setRole() {

		new SuccessfulReport().setRole("role");
	}

	@Test
	public void getFlag() {

		assertNull(new SuccessfulReport().getFlag());
	}

	@Test
	public void setFlag() {

		new SuccessfulReport().setFlag("flag");
	}
}