package com.onlinevalidator.service.impl;

import com.onlinevalidator.dto.RappresentazioneViewer;
import com.onlinevalidator.service.RappresentazioneServiceInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


/**
 * @author Manuel Gozzi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/bean-servlet-test.xml"})
public class RappresentazioneServiceTest {

	@Autowired
	private RappresentazioneServiceInterface rappresentazioneServiceInterface;

	@Test
	public void init() {

		try {

			((RappresentazioneService) rappresentazioneServiceInterface).init();
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void filtraRappresentazioniPerTipoDocumento() {

		try {
			List<RappresentazioneViewer> rappresentazioneViewers = rappresentazioneServiceInterface.filtraRappresentazioniPerTipoDocumento(1);
			assertNotNull(rappresentazioneViewers);
			assertEquals(2, rappresentazioneViewers.size());
			assertNotNull(rappresentazioneViewers.get(0).getIdRappresentazione());
			assertNotNull(rappresentazioneViewers.get(0).getDsDescrizione());

			rappresentazioneViewers = rappresentazioneServiceInterface.filtraRappresentazioniPerTipoDocumento(2);
			assertNotNull(rappresentazioneViewers);
			assertEquals(2, rappresentazioneViewers.size());
			assertNotNull(rappresentazioneViewers.get(0).getIdRappresentazione());
			assertNotNull(rappresentazioneViewers.get(0).getDsDescrizione());

			rappresentazioneViewers = rappresentazioneServiceInterface.filtraRappresentazioniPerTipoDocumento(3);
			assertNotNull(rappresentazioneViewers);
			assertEquals(4, rappresentazioneViewers.size());
			assertNotNull(rappresentazioneViewers.get(0).getIdRappresentazione());
			assertNotNull(rappresentazioneViewers.get(0).getDsDescrizione());
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}
}