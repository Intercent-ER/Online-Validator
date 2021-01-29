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
	public void getName() {

		assertNull(new OvValidatore().getName());
	}

	@Test
	public void setName() {

		new OvValidatore().setName("nome validatore");
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
}