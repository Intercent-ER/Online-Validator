package com.onlinevalidator.constraint;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Gozzi
 */
public class IdValidatorTest {

	@Test
	public void initialize() {
		new IdValidator().initialize(null);
	}

	@Test
	public void isValid() {

		IdValidator idValidator = new IdValidator();
		boolean mustBeFalse = idValidator.isValid(0, null);
		assertFalse(mustBeFalse);

		mustBeFalse = idValidator.isValid(null, null);
		assertFalse(mustBeFalse);

		boolean mustBeTrue = idValidator.isValid(10, null);
		assertTrue(mustBeTrue);

		mustBeTrue = idValidator.isValid(1, null);
		assertTrue(mustBeTrue);
	}
}