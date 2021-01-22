package com.onlinevalidator.service.impl;

import com.onlinevalidator.exception.ConfigurationNotFoundException;
import com.onlinevalidator.model.OvConfigurazione;
import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import com.onlinevalidator.repository.OvConfigurazioneJpaRepository;
import com.onlinevalidator.service.ConfigurazioneServiceInterface;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Manuel Gozzi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/bean-servlet-test.xml"})
public class ConfigurazioneServiceTest {

	@Autowired
	private ConfigurazioneServiceInterface configurazioneService;

	@Autowired
	private OvConfigurazioneJpaRepository configurazioneJpaRepository;

	private OvConfigurazione ovConfigurazione;

	@Before
	public void before() {
		ovConfigurazione = new OvConfigurazione();
		ovConfigurazione.setIdConfigurazione(-100);
		ovConfigurazione.setCdChiaveConfigurazione(ChiaveConfigurazioneEnum.NULL);
		ovConfigurazione.setDsDescrizione("null");
		ovConfigurazione.setCdValoreConfigurazione("vuoto");
		ovConfigurazione = configurazioneJpaRepository.save(ovConfigurazione);
	}

	@After
	public void after() {

		configurazioneJpaRepository.delete(ovConfigurazione);
	}

	@Test
	public void init() {
		((ConfigurazioneService) configurazioneService).init();
	}

	@Test
	public void readValue() {

		String valore = configurazioneService.readValue(ChiaveConfigurazioneEnum.NULL);
		Assert.assertEquals("vuoto", valore);

		try {
			valore = configurazioneService.readValue(null);

		} catch (Exception e) {

			Assert.assertTrue(e instanceof ConfigurationNotFoundException);
			Assert.assertEquals("Chiave di configurazione null", e.getMessage());
		}
	}

	@Test
	public void testReadValue() {

		String valore = configurazioneService.readValue(ChiaveConfigurazioneEnum.NULL, false);
		Assert.assertEquals("vuoto", valore);
	}
}