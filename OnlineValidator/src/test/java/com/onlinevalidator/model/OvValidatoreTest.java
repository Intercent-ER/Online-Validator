package com.onlinevalidator.model;

import com.onlinevalidator.model.enumerator.TipoFileEnum;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Manuel Gozzi
 */
public class OvValidatoreTest {

	@Test
	public void getIdValidatore() {

		assertEquals(0, new OvValidatore().getIdValidatore());
	}

	@Test
	public void setIdValidatore() {

		new OvValidatore().setIdValidatore(0);
	}

	@Test
	public void getBlFile() {

		assertNull(new OvValidatore().getBlFile());
	}

	@Test
	public void setBlFile() {

		new OvValidatore().setBlFile("a string".getBytes(StandardCharsets.UTF_8));
	}

	@Test
	public void getNmNome() {

		assertNull(new OvValidatore().getNmNome());
	}

	@Test
	public void setNmNome() {

		new OvValidatore().setNmNome("nome validatore");
	}

	@Test
	public void getCdTipoFile() {

		assertNull(new OvValidatore().getCdTipoFile());
	}

	@Test
	public void setCdTipoFile() {

		OvValidatore validatore = new OvValidatore();
		for (TipoFileEnum value : TipoFileEnum.values()) {

			validatore.setCdTipoFile(value);
		}
	}

	@Test
	public void getOvRappresentazione() {

		assertNull(new OvValidatore().getOvRappresentazione());
	}

	@Test
	public void setOvRappresentazione() {

		new OvValidatore().setOvRappresentazione(null);
	}

	@Test
	public void getNiVersione() {

		assertNull(new OvValidatore().getNiVersione());
	}

	@Test
	public void setNiVersione() {

		new OvValidatore().setNiVersione("1.0");
	}
}