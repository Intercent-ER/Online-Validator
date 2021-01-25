package com.onlinevalidator.generatedsources.xsd.rendering;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Manuel Gozzi
 */
public class EsitoValidazioneTest {

	@Test
	public void value() {

		assertEquals("OK", EsitoValidazione.OK.value());
		assertEquals("WARNING", EsitoValidazione.WARNING.value());
		assertEquals("FATAL", EsitoValidazione.FATAL.value());
	}

	@Test
	public void fromValue() {

		assertEquals(EsitoValidazione.fromValue("OK"), EsitoValidazione.OK);
		assertEquals(EsitoValidazione.fromValue("WARNING"), EsitoValidazione.WARNING);
		assertEquals(EsitoValidazione.fromValue("FATAL"), EsitoValidazione.FATAL);
	}

	@Test
	public void values() {

		assertNotNull(EsitoValidazione.values());
		assertEquals(3, EsitoValidazione.values().length);
	}

	@Test
	public void valueOf() {

		assertEquals(EsitoValidazione.valueOf("OK"), EsitoValidazione.OK);
		assertEquals(EsitoValidazione.valueOf("WARNING"), EsitoValidazione.WARNING);
		assertEquals(EsitoValidazione.valueOf("FATAL"), EsitoValidazione.FATAL);
	}
}