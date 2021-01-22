package com.onlinevalidator.exception.rendering;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Gozzi
 */
public class XmlRenderingExceptionTest {

	@Test
	public void testConstructor() {

		try {

			throw new XmlRenderingException();
		} catch (Exception e) {

			assertTrue(e instanceof XmlRenderingException);
		}

		try {

			throw new XmlRenderingException("messaggio");
		} catch (Exception e) {

			assertTrue(e instanceof XmlRenderingException);
			assertEquals("messaggio", e.getMessage());
		}

		try {

			throw new XmlRenderingException(new IllegalStateException("illegal"));
		} catch (Exception e) {

			assertTrue(e instanceof XmlRenderingException);
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}

		try {

			throw new XmlRenderingException("messaggio", new IllegalStateException("illegal"));
		} catch (Exception e) {

			assertTrue(e instanceof XmlRenderingException);
			assertEquals(e.getMessage(), "messaggio");
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}

		try {

			throw new XmlRenderingException("messaggio", new IllegalStateException("illegal"), true, true);
		} catch (Exception e) {

			assertTrue(e instanceof XmlRenderingException);
			assertEquals(e.getMessage(), "messaggio");
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}
	}
}