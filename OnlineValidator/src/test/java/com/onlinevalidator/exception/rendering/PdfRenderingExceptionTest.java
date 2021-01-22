package com.onlinevalidator.exception.rendering;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Gozzi
 */
public class PdfRenderingExceptionTest {

	@Test
	public void testConstructor() {

		try {

			throw new PdfRenderingException();
		} catch (Exception e) {

			assertTrue(e instanceof PdfRenderingException);
		}

		try {

			throw new PdfRenderingException("messaggio");
		} catch (Exception e) {

			assertTrue(e instanceof PdfRenderingException);
			assertEquals("messaggio", e.getMessage());
		}

		try {

			throw new PdfRenderingException("messaggio", new IllegalStateException("illegal"));
		} catch (Exception e) {

			assertTrue(e instanceof PdfRenderingException);
			assertEquals(e.getMessage(), "messaggio");
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}

		try {

			throw new PdfRenderingException(new IllegalStateException("illegal"));
		} catch (Exception e) {

			assertTrue(e instanceof PdfRenderingException);
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}

		try {

			throw new PdfRenderingException("messaggio", new IllegalStateException("illegal"), true, true);
		} catch (Exception e) {

			assertTrue(e instanceof PdfRenderingException);
			assertEquals(e.getMessage(), "messaggio");
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}
	}
}