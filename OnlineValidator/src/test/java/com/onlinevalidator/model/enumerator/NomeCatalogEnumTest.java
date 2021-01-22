package com.onlinevalidator.model.enumerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuel Gozzi
 */
public class NomeCatalogEnumTest {

	@Test
	public void testToString() {

		NomeCatalogEnum nomeCatalogEnum = NomeCatalogEnum.CategoriaImposte;
		assertEquals("CategoriaImposte", nomeCatalogEnum.toString());
	}
}