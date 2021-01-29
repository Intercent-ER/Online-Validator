package com.onlinevalidator.model;

import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Manuel Gozzi
 */
public class OvConfigurazioneTest {

	@Test
	public void getIdConfigurazione() {

		assertEquals(0, new OvConfigurazione().getIdConfigurazione());
	}

	@Test
	public void setIdConfigurazione() {

		new OvConfigurazione().setIdConfigurazione(0);
	}

	@Test
	public void getCdChiaveConfigurazione() {

		assertNull(new OvConfigurazione().getCdChiaveConfigurazione());
	}

	@Test
	public void setCdChiaveConfigurazione() {

		OvConfigurazione ovConfigurazione = new OvConfigurazione();
		for (ChiaveConfigurazioneEnum value : ChiaveConfigurazioneEnum.values()) {

			ovConfigurazione.setCdChiaveConfigurazione(value);
		}
	}

	@Test
	public void getCdValoreConfigurazione() {

		assertNull(new OvConfigurazione().getCdValoreConfigurazione());
	}

	@Test
	public void setCdValoreConfigurazione() {

		new OvConfigurazione().setCdValoreConfigurazione("valore");
	}

	@Test
	public void getDsDescrizione() {

		assertNull(new OvConfigurazione().getDsDescrizione());
	}

	@Test
	public void setDsDescrizione() {

		new OvConfigurazione().setDsDescrizione("descrizione");
	}
}