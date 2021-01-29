package com.onlinevalidator.dto;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Manuel Gozzi
 */
public class ValidationAssertTest {

	@Test
	public void getTest() {

		ValidationAssert validationAssert = new ValidationAssert();
		assertNull(validationAssert.getTest());
	}

	@Test
	public void setTest() {

		ValidationAssert validationAssert = new ValidationAssert();
		validationAssert.setTest("test");
	}

	@Test
	public void getLocation() {

		ValidationAssert validationAssert = new ValidationAssert();
		assertNull(validationAssert.getLocation());
	}

	@Test
	public void setLocation() {

		ValidationAssert validationAssert = new ValidationAssert();
		validationAssert.setLocation("location");
	}

	@Test
	public void getTesto() {

		ValidationAssert validationAssert = new ValidationAssert();
		assertNull(validationAssert.getTesto());
	}

	@Test
	public void setTesto() {

		ValidationAssert validationAssert = new ValidationAssert();
		validationAssert.setTesto("prova");
	}

	@Test
	public void setWarning() {

		ValidationAssert validationAssert = new ValidationAssert();
		validationAssert.setWarning(true);
		validationAssert.setWarning(false);
	}

	@Test
	public void testToString() {

		ValidationAssert validationAssert = new ValidationAssert();
		validationAssert.setWarning(true);
		validationAssert.setTest("test");
		validationAssert.setTesto("descrizione di prova");
		validationAssert.setFatal(false);
		validationAssert.setLocation("location/row:test");

		String expected = "ValidationAssert [test=test, location=location/row:test, testo=descrizione di prova, warning=true, fatal=false]";
		assertEquals(expected, validationAssert.toString());
	}

	@Test
	public void setFatal() {

		ValidationAssert validationAssert = new ValidationAssert();
		validationAssert.setFatal(true);
		validationAssert.setFatal(false);
	}

	@Test
	public void isWarning() {

		ValidationAssert testCase = new ValidationAssert("test", "location", "testo", "warning");
		assertTrue(testCase.isWarning());

		testCase = new ValidationAssert("test", "location", "testo", "WARNING");
		assertTrue(testCase.isWarning());

		testCase = new ValidationAssert("test", "location", "testo", "");
		assertFalse(testCase.isWarning());

		testCase = new ValidationAssert("test", "location", "testo", null);
		assertFalse(testCase.isWarning());
	}

	@Test
	public void isFatal() {

		ValidationAssert testCase = new ValidationAssert("test", "location", "testo", "fatal");
		assertTrue(testCase.isFatal());

		testCase = new ValidationAssert("test", "location", "testo", "FATAL");
		assertTrue(testCase.isFatal());

		testCase = new ValidationAssert("test", "location", "testo", "");
		assertFalse(testCase.isFatal());

		testCase = new ValidationAssert("test", "location", "testo", null);
		assertFalse(testCase.isFatal());
	}
}