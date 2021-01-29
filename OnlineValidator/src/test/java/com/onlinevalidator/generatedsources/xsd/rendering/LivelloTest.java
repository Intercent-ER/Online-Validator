package com.onlinevalidator.generatedsources.xsd.rendering;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Manuel Gozzi
 */
public class LivelloTest {

	@Test
	public void value() {

		assertEquals("WARNING", Livello.WARNING.value());
		assertEquals("FATAL", Livello.FATAL.value());
	}

	@Test
	public void fromValue() {

		assertEquals(Livello.fromValue("WARNING"), Livello.WARNING);
		assertEquals(Livello.fromValue("FATAL"), Livello.FATAL);
	}

	@Test
	public void values() {

		assertNotNull(Livello.values());
		assertEquals(2, Livello.values().length);
	}

	@Test
	public void valueOf() {

		assertEquals(Livello.valueOf("WARNING"), Livello.WARNING);
		assertEquals(Livello.valueOf("FATAL"), Livello.FATAL);
	}
}