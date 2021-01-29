package com.onlinevalidator.generatedsources.xsd.rendering;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * @author Manuel Gozzi
 */
public class RapportoValidazioneTest {

	@Test
	public void getVersione() {

		assertNull(new RapportoValidazione().getVersione());
	}

	@Test
	public void setVersione() {

		new RapportoValidazione().setVersione("1.0");
	}

	@Test
	public void getEsito() {

		assertNull(new RapportoValidazione().getEsito());
	}

	@Test
	public void setEsito() {

		new RapportoValidazione().setEsito(EsitoValidazione.OK);
	}

	@Test
	public void getDataValidazione() {

		assertNull(new RapportoValidazione().getDataValidazione());
	}

	@Test
	public void setDataValidazione() {

		new RapportoValidazione().setDataValidazione("2021-01-20 10:00:00");
	}

	@Test
	public void getErroreXSD() {

		assertNull(new RapportoValidazione().getErroreXSD());
	}

	@Test
	public void setErroreXSD() {

		new RapportoValidazione().setErroreXSD("errore XSD");
	}

	@Test
	public void getListaAssertValidazione() {

		assertNull(new RapportoValidazione().getListaAssertValidazione());
	}

	@Test
	public void setListaAssertValidazione() {

		new RapportoValidazione().setListaAssertValidazione(new ListaAssertValidazioneType());
	}
}