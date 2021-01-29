package com.onlinevalidator.generatedsources.xsd.rendering;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Manuel Gozzi
 */
public class ObjectFactoryTest {

	@Test
	public void createRapportoValidazione() {

		assertNotNull(new ObjectFactory().createRapportoValidazione());
	}

	@Test
	public void createListaAssertValidazioneType() {

		assertNotNull(new ObjectFactory().createListaAssertValidazioneType());
	}

	@Test
	public void createAssertValidazioneType() {

		assertNotNull(new ObjectFactory().createAssertValidazioneType());
	}
}