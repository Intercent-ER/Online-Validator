package com.onlinevalidator.generatedsources.xsd.rendering;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Gozzi
 */
public class ListaAssertValidazioneTypeTest {

	@Test
	public void getAssertValidazione() {

		ListaAssertValidazioneType listaAssertValidazioneType = new ListaAssertValidazioneType();
		assertNotNull(listaAssertValidazioneType.getAssertValidazione());
		assertTrue(listaAssertValidazioneType.getAssertValidazione().isEmpty());
	}
}