package com.onlinevalidator.model;

import com.onlinevalidator.model.enumerator.NomeCatalogEnum;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;


/**
 * @author Manuel Gozzi
 */
public class OvCatalogTest {

	@Test
	public void getIdCatalog() {

		assertEquals(0, new OvCatalog().getIdCatalog());
	}

	@Test
	public void setIdCatalog() {

		new OvCatalog().setIdCatalog(0);
	}

	@Test
	public void getDsDescrizione() {

		assertNull(new OvCatalog().getDsDescrizione());
	}

	@Test
	public void setDsDescrizione() {

		new OvCatalog().setDsDescrizione("descrizione");
	}

	@Test
	public void getBlFileCatalog() {

		assertNull(new OvCatalog().getBlFileCatalog());
	}

	@Test
	public void getBlFileCatalogAsString() {

		OvCatalog catalog = new OvCatalog();
		assertNull(catalog.getBlFileCatalogAsString());
		catalog.setBlFileCatalog("a string".getBytes(StandardCharsets.UTF_8));
		assertNotNull(catalog.getBlFileCatalogAsString());
	}

	@Test
	public void setBlFileCatalog() {

		new OvCatalog().setBlFileCatalog("a string".getBytes(StandardCharsets.UTF_8));
	}

	@Test
	public void getNmNome() {

		assertNull(new OvCatalog().getNmNome());
	}

	@Test
	public void setNmNome() {

		OvCatalog ovCatalog = new OvCatalog();
		for (NomeCatalogEnum value : NomeCatalogEnum.values()) {

			ovCatalog.setNmNome(value);
		}
	}

	@Test
	public void getCdVersione() {

		assertNull(new OvCatalog().getCdVersione());
	}

	@Test
	public void setCdVersione() {

		new OvCatalog().setCdVersione("1.0");
	}

	@Test
	public void getCdParametroXsl() {

		assertNull(new OvCatalog().getCdParametroXsl());
	}

	@Test
	public void setCdParametroXsl() {

		new OvCatalog().setCdParametroXsl("string");
	}
}