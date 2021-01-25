package com.onlinevalidator.generatedsources.xsd.rendering;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * @author Manuel Gozzi
 */
public class AssertValidazioneTypeTest {

	private AssertValidazioneType validazioneType;

	@Before
	public void before() {
		this.validazioneType = new AssertValidazioneType();
	}

	@Test
	public void getLivelloErrore() {

		assertNull(validazioneType.getLivelloErrore());
	}

	@Test
	public void setLivelloErrore() {

		validazioneType.setLivelloErrore(Livello.FATAL);
	}

	@Test
	public void getPosizione() {

		assertNull(validazioneType.getPosizione());
	}

	@Test
	public void setPosizione() {

		validazioneType.setPosizione("posizione");
	}

	@Test
	public void getTest() {

		assertNull(validazioneType.getTest());
	}

	@Test
	public void setTest() {

		validazioneType.setTest("test");
	}

	@Test
	public void getDescrizione() {

		assertNull(validazioneType.getDescrizione());
	}

	@Test
	public void setDescrizione() {

		validazioneType.setDescrizione("descrizione");
	}
}