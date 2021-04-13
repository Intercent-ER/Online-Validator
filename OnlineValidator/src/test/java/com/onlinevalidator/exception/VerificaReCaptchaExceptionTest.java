package com.onlinevalidator.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Gozzi
 */
public class VerificaReCaptchaExceptionTest {

	@Test
	public void testConstructor() {
		try {

			throw new VerificaReCaptchaException();
		} catch (Exception e) {

			assertTrue(e instanceof VerificaReCaptchaException);
		}

		try {

			throw new VerificaReCaptchaException("messaggio");
		} catch (Exception e) {

			assertTrue(e instanceof VerificaReCaptchaException);
			assertEquals("messaggio", e.getMessage());
		}

		try {

			throw new VerificaReCaptchaException("messaggio", new IllegalStateException("illegal"));
		} catch (Exception e) {

			assertTrue(e instanceof VerificaReCaptchaException);
			assertEquals(e.getMessage(), "messaggio");
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}

		try {

			throw new VerificaReCaptchaException(new IllegalStateException("illegal"));
		} catch (Exception e) {

			assertTrue(e instanceof VerificaReCaptchaException);
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}

		try {

			throw new VerificaReCaptchaException("messaggio", new IllegalStateException("illegal"), true, true);
		} catch (Exception e) {

			assertTrue(e instanceof VerificaReCaptchaException);
			assertEquals(e.getMessage(), "messaggio");
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}
	}
}