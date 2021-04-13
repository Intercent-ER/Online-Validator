package com.onlinevalidator.model;

import com.onlinevalidator.model.enumerator.RappresentazionePaeseEnum;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Manuel Gozzi
 */
public class OvRappresentazioneTest {

	@Test
	public void getIdRappresentazione() {
		assertEquals(0, new OvRappresentazione().getIdRappresentazione());
	}

	@Test
	public void setIdRappresentazione() {
		new OvRappresentazione().setIdRappresentazione(0);
	}

	@Test
	public void getCdProcessTypeIdentifier() {
		assertNull(new OvRappresentazione().getCdProcessTypeIdentifier());
	}

	@Test
	public void setCdProcessTypeIdentifier() {
		new OvRappresentazione().setCdProcessTypeIdentifier(null);
		new OvRappresentazione().setCdProcessTypeIdentifier("null");
	}

	@Test
	public void getCdDocumentTypeIdentifier() {
		assertNull(new OvRappresentazione().getCdDocumentTypeIdentifier());
	}

	@Test
	public void setCdDocumentTypeIdentifier() {
		new OvRappresentazione().setCdProcessTypeIdentifier(null);
		new OvRappresentazione().setCdDocumentTypeIdentifier("null");
	}

	@Test
	public void getDsNomeRappresentazione() {
		assertNull(new OvRappresentazione().getDsNomeRappresentazione());
	}

	@Test
	public void setDsNomeRappresentazione() {
		new OvRappresentazione().setDsNomeRappresentazione(null);
		new OvRappresentazione().setDsNomeRappresentazione("null");
	}

	@Test
	public void getNiVersione() {
		assertNull(new OvRappresentazione().getNiVersione());
	}

	@Test
	public void setNiVersione() {
		new OvRappresentazione().setNiVersione(null);
		new OvRappresentazione().setNiVersione("null");
	}

	@Test
	public void getCdRappresentazionePaeseEnum() {
		assertNull(new OvRappresentazione().getCdRappresentazionePaeseEnum());
	}

	@Test
	public void setCdRappresentazionePaeseEnum() {
		OvRappresentazione ovRappresentazione = new OvRappresentazione();
		ovRappresentazione.setCdRappresentazionePaeseEnum(null);
		Arrays.stream(RappresentazionePaeseEnum.values())
				.forEach(ovRappresentazione::setCdRappresentazionePaeseEnum);
	}

	@Test
	public void getOvValidatore() {
		assertNull(new OvRappresentazione().getOvValidatore());
	}

	@Test
	public void setOvValidatore() {
		new OvRappresentazione().setOvValidatore(null);
		new OvRappresentazione().setOvValidatore(Collections.emptyList());
		new OvRappresentazione().setOvValidatore(Arrays.asList(new OvValidatore(), new OvValidatore(), null));
	}

	@Test
	public void getOvTipoDocumento() {
		assertNull(new OvRappresentazione().getOvTipoDocumento());
	}

	@Test
	public void setOvTipoDocumento() {
		new OvRappresentazione().setOvTipoDocumento(null);
		new OvRappresentazione().setOvTipoDocumento(new OvTipoDocumento());
	}

	@Test
	public void getDsFormato() {
		assertNull(new OvRappresentazione().getDsFormato());
	}

	@Test
	public void setDsFormato() {
		new OvRappresentazione().setDsFormato(null);
		new OvRappresentazione().setDsFormato("formato");
	}
}