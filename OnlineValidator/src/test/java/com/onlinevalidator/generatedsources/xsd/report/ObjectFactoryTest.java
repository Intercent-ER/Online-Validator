package com.onlinevalidator.generatedsources.xsd.report;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Manuel Gozzi
 */
public class ObjectFactoryTest {

	@Test
	public void createTpReport() {

		assertNotNull(new ObjectFactory().createTpReport());
	}

	@Test
	public void createSchematronOutput() {

		assertNotNull(new ObjectFactory().createSchematronOutput());
	}

	@Test
	public void createNsPrefixInAttributeValues() {

		assertNotNull(new ObjectFactory().createNsPrefixInAttributeValues());
	}

	@Test
	public void createActivePattern() {

		assertNotNull(new ObjectFactory().createActivePattern());
	}

	@Test
	public void createFiredRule() {

		assertNotNull(new ObjectFactory().createFiredRule());
	}

	@Test
	public void createFailedAssert() {

		assertNotNull(new ObjectFactory().createFailedAssert());
	}

	@Test
	public void createDiagnosticReference() {

		assertNotNull(new ObjectFactory().createDiagnosticReference());
	}

	@Test
	public void createSuccessfulReport() {

		assertNotNull(new ObjectFactory().createSuccessfulReport());
	}

	@Test
	public void createReport() {

		assertNotNull(new ObjectFactory().createReport(
				new ObjectFactory().createTpReport()
		));
	}

	@Test
	public void createText() {

		assertNotNull(new ObjectFactory().createText("text"));
	}
}