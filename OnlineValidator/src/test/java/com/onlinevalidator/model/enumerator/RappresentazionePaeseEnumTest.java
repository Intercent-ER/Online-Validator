package com.onlinevalidator.model.enumerator;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuel Gozzi
 */
public class RappresentazionePaeseEnumTest {

	@Test
	public void testToString() {
		Arrays.stream(RappresentazionePaeseEnum.values())
				.forEach(v -> assertEquals(v.name(), v.toString()));
	}

	@Test
	public void values() {
		assertEquals(2, RappresentazionePaeseEnum.values().length);
	}
}