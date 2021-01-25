package com.onlinevalidator.generatedsources.xsd.report;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * @author Manuel Gozzi
 */
public class NsPrefixInAttributeValuesTest {

	@Test
	public void getPrefix() {

		assertNull(new NsPrefixInAttributeValues().getPrefix());
	}

	@Test
	public void setPrefix() {

		new NsPrefixInAttributeValues().setPrefix("ps");
	}

	@Test
	public void getUri() {

		assertNull(new NsPrefixInAttributeValues().getUri());
	}

	@Test
	public void setUri() {

		new NsPrefixInAttributeValues().setUri("https://google.com");
	}
}