package com.onlinevalidator.pojo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuel Gozzi
 */
public class TipoRenderingEnumTest {

	@Test
	public void testToString() {

		assertEquals(TipoRenderingEnum.XML.name(), TipoRenderingEnum.XML.toString());
		assertEquals(TipoRenderingEnum.PDF.name(), TipoRenderingEnum.PDF.toString());
	}
}