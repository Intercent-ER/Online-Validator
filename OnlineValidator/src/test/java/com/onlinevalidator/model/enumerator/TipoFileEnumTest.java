package com.onlinevalidator.model.enumerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuel Gozzi
 */
public class TipoFileEnumTest {

	@Test
	public void testToString() {

		TipoFileEnum tipoFileEnum = TipoFileEnum.XSD;
		assertEquals("XSD", tipoFileEnum.toString());
	}
}