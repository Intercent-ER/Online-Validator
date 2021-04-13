package com.onlinevalidator.dto;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Manuel Gozzi
 */
public class RappresentazioneViewerTest {

	@Test
	public void constructors() {

		try {

			new RappresentazioneViewer();
			new RappresentazioneViewer(10, "descrizione");
			new RappresentazioneViewer(null, "descrizione");
			new RappresentazioneViewer(10, null);
			new RappresentazioneViewer(null, null);
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void getIdRappresentazione() {

		try {

			assertEquals(new Integer(10), new RappresentazioneViewer(10, null).getIdRappresentazione());
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void setIdRappresentazione() {

		try {

			RappresentazioneViewer rappresentazioneViewer = new RappresentazioneViewer(10, null);
			rappresentazioneViewer.setIdRappresentazione(500);
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void getDsDescrizione() {

		try {

			assertNull(new RappresentazioneViewer(10, null).getDsDescrizione());
			assertEquals("descrizione", new RappresentazioneViewer(null, "descrizione").getDsDescrizione());
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void setDsDescrizione() {

		try {

			RappresentazioneViewer rappresentazioneViewer = new RappresentazioneViewer(10, null);
			rappresentazioneViewer.setDsDescrizione("descrizione");
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}
}