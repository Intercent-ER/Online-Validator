package com.onlinevalidator.generatedsources.xsd.report;

import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Gozzi
 */
public class SchematronOutputTest {

	@Test
	public void getText() {

		assertTrue(new SchematronOutput().getText().isEmpty());
	}

	@Test
	public void getNsPrefixInAttributeValues() {

		assertTrue(new SchematronOutput().getNsPrefixInAttributeValues().isEmpty());
	}

	@Test
	public void getActivePatternAndFiredRuleAndFailedAssert() {

		assertTrue(new SchematronOutput().getActivePatternAndFiredRuleAndFailedAssert().isEmpty());
	}

	@Test
	public void getTitle() {

		assertNull(new SchematronOutput().getTitle());
	}

	@Test
	public void setTitle() {

		new SchematronOutput().setTitle("title");
	}

	@Test
	public void getPhase() {

		assertNull(new SchematronOutput().getPhase());
	}

	@Test
	public void setPhase() {

		new SchematronOutput().setPhase("validate");
	}

	@Test
	public void getSchemaVersion() {

		assertNull(new SchematronOutput().getSchemaVersion());
	}

	@Test
	public void setSchemaVersion() {

		new SchematronOutput().setSchemaVersion("1.0");
	}
}