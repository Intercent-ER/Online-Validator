package com.onlinevalidator.model.enumerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuel Gozzi
 */
public class ChiaveConfigurazioneEnumTest {

	@Test
	public void testToString() {

		ChiaveConfigurazioneEnum chiaveConfigurazioneEnum = ChiaveConfigurazioneEnum.CONTEXT_PATH;
		assertEquals("CONTEXT_PATH", chiaveConfigurazioneEnum.toString());
	}
}