package com.onlinevalidator.exception;

import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Gozzi
 */
public class ConfigurationNotFoundExceptionTest {

	@Test
	public void notFound() {

		try {
			throw ConfigurationNotFoundException.notFound(ChiaveConfigurazioneEnum.CONTEXT_PATH);
		} catch (Exception e) {
			assertTrue(e instanceof ConfigurationNotFoundException);
			assertEquals(
					"Non è stato possibile recuperare il valore della configurazione CONTEXT_PATH",
					e.getMessage()
			);
		}

		try {
			throw ConfigurationNotFoundException.notFound(null);
		} catch (Exception e) {
			assertTrue(e instanceof ConfigurationNotFoundException);
			assertEquals(
					"Non è stato possibile recuperare il valore della configurazione null",
					e.getMessage()
			);
		}
	}

	@Test
	public void testConstructor() {
		try {

			throw new ConfigurationNotFoundException();
		} catch (Exception e) {

			assertTrue(e instanceof ConfigurationNotFoundException);
		}

		try {

			throw new ConfigurationNotFoundException("messaggio");
		} catch (Exception e) {

			assertTrue(e instanceof ConfigurationNotFoundException);
			assertEquals("messaggio", e.getMessage());
		}

		try {

			throw new ConfigurationNotFoundException("messaggio", new IllegalStateException("illegal"));
		} catch (Exception e) {

			assertTrue(e instanceof ConfigurationNotFoundException);
			assertEquals(e.getMessage(), "messaggio");
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}

		try {

			throw new ConfigurationNotFoundException(new IllegalStateException("illegal"));
		} catch (Exception e) {

			assertTrue(e instanceof ConfigurationNotFoundException);
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}

		try {

			throw new ConfigurationNotFoundException("messaggio", new IllegalStateException("illegal"), true, true);
		} catch (Exception e) {

			assertTrue(e instanceof ConfigurationNotFoundException);
			assertEquals(e.getMessage(), "messaggio");
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}
	}
}