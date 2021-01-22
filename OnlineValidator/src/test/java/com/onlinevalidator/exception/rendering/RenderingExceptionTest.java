package com.onlinevalidator.exception.rendering;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Gozzi
 */
public class RenderingExceptionTest {

	@Test
	public void testConstructor() {

		try {

			throw new RenderingException();
		} catch (Exception e) {

			assertTrue(e instanceof RenderingException);
		}

		try {

			throw new RenderingException("messaggio");
		} catch (Exception e) {

			assertTrue(e instanceof RenderingException);
			assertEquals("messaggio", e.getMessage());
		}

		try {

			throw new RenderingException(new IllegalStateException("illegal"));
		} catch (Exception e) {

			assertTrue(e instanceof RenderingException);
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}

		try {

			throw new RenderingException("messaggio", new IllegalStateException("illegal"));
		} catch (Exception e) {

			assertTrue(e instanceof RenderingException);
			assertEquals(e.getMessage(), "messaggio");
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}

		try {

			throw new RenderingException("messaggio", new IllegalStateException("illegal"), true, true);
		} catch (Exception e) {

			assertTrue(e instanceof RenderingException);
			assertEquals(e.getMessage(), "messaggio");
			assertTrue(e.getCause() instanceof IllegalStateException);
			assertEquals("illegal", e.getCause().getMessage());
		}
	}

}