package com.onlinevalidator.pojo;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Gozzi
 */
public class ValidationAssertTest {

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