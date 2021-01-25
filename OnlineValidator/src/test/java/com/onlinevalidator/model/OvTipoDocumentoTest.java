package com.onlinevalidator.model;

import com.onlinevalidator.model.enumerator.NomeTipoDocumentoEnum;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Manuel Gozzi
 */
public class OvTipoDocumentoTest {

	@Test
	public void getIdTipoDocumento() {

		assertEquals(0, new OvTipoDocumento().getIdTipoDocumento());
	}

	@Test
	public void setIdTipoDocumento() {

		new OvTipoDocumento().setIdTipoDocumento(0);
	}

	@Test
	public void getName() {

		assertNull(new OvTipoDocumento().getName());
	}

	@Test
	public void setName() {

		OvTipoDocumento tipoDocumento = new OvTipoDocumento();
		for (NomeTipoDocumentoEnum value : NomeTipoDocumentoEnum.values()) {

			tipoDocumento.setName(value);
		}
	}

	@Test
	public void getValidatori() {

		assertNull(new OvTipoDocumento().getValidatori());
	}

	@Test
	public void setValidatori() {

		new OvTipoDocumento().setValidatori(Collections.emptyList());
	}
}