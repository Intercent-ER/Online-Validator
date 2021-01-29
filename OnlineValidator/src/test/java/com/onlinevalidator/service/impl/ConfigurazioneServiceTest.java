package com.onlinevalidator.service.impl;

import com.onlinevalidator.exception.ConfigurationNotFoundException;
import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import com.onlinevalidator.service.ConfigurazioneServiceInterface;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

	@Test
	public void init() {

		try {

			// Verifico che l'inizializzazione non generi errori
			((ConfigurazioneService) configurazioneService).init();
		} catch (Exception e) {

			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void readValueNoParam() {

		ConfigurazioneService configurazioneServiceSpy = Mockito.spy((ConfigurazioneService) configurazioneService);
		ChiaveConfigurazioneEnum chiaveDiConfigurazione = ChiaveConfigurazioneEnum.CONTEXT_PATH;

		try {

			String valore = configurazioneServiceSpy.readValue(chiaveDiConfigurazione);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readValue(chiaveDiConfigurazione, true);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(0)).readValue(chiaveDiConfigurazione, false);

			// Devo leggere dalla cache
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readFromCache(chiaveDiConfigurazione);

			// Al primo tentativo devo leggere dal database per popolare la cache
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readFromDatabase(chiaveDiConfigurazione);
			Assert.assertNotNull(valore);
			Assert.assertFalse(valore.isEmpty());

			// Verifico che al secondo accesso legga dalla cache
			valore = configurazioneServiceSpy.readValue(chiaveDiConfigurazione);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(2)).readValue(chiaveDiConfigurazione, true);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(0)).readValue(chiaveDiConfigurazione, false);

			// Devo leggere da cache
			Mockito.verify(configurazioneServiceSpy, Mockito.times(2)).readFromCache(chiaveDiConfigurazione);

			// Non devo leggere da database
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readFromDatabase(chiaveDiConfigurazione);
			Assert.assertNotNull(valore);
			Assert.assertFalse(valore.isEmpty());

		} catch (ConfigurationNotFoundException e) {

			Assert.fail(
					String.format(
							"La configurazione %s deve essere impostata",
							chiaveDiConfigurazione
					)
			);
		}

		try {

			configurazioneServiceSpy.readValue(null);
			Assert.fail("La ricerca di un valore con chiave configurazione null deve tirare eccezione");
		} catch (Exception e) {

			Assert.assertTrue(e instanceof ConfigurationNotFoundException);
			Assert.assertEquals("Chiave di configurazione null", e.getMessage());

			// Se ho un null in ingresso non devo procedere con ulteriori funzioni
			Mockito.verify(configurazioneServiceSpy, Mockito.times(2)).readValue(chiaveDiConfigurazione, true);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(0)).readValue(chiaveDiConfigurazione, false);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(2)).readFromCache(chiaveDiConfigurazione);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readFromDatabase(chiaveDiConfigurazione);
		}
	}

	@Test
	public void readValueWithParam() {

		ConfigurazioneService configurazioneServiceSpy = Mockito.spy((ConfigurazioneService) configurazioneService);
		ChiaveConfigurazioneEnum chiaveDiConfigurazione = ChiaveConfigurazioneEnum.CONTEXT_PATH;

		try {

			String valore = configurazioneServiceSpy.readValue(chiaveDiConfigurazione, true);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readValue(chiaveDiConfigurazione, true);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(0)).readValue(chiaveDiConfigurazione, false);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(0)).readValue(chiaveDiConfigurazione);

			// Devo leggere dalla cache
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readFromCache(chiaveDiConfigurazione);

			// Al primo tentativo devo leggere dal database per popolare la cache
			Mockito.verify(configurazioneServiceSpy, Mockito.times(0)).readFromDatabase(chiaveDiConfigurazione);
			Assert.assertNotNull(valore);
			Assert.assertFalse(valore.isEmpty());

			valore = configurazioneServiceSpy.readValue(chiaveDiConfigurazione, true);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(2)).readValue(chiaveDiConfigurazione, true);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(0)).readValue(chiaveDiConfigurazione, false);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(0)).readValue(chiaveDiConfigurazione);

			// Devo leggere dalla cache
			Mockito.verify(configurazioneServiceSpy, Mockito.times(2)).readFromCache(chiaveDiConfigurazione);

			// Non devo leggere da database
			Mockito.verify(configurazioneServiceSpy, Mockito.times(0)).readFromDatabase(chiaveDiConfigurazione);
			Assert.assertNotNull(valore);
			Assert.assertFalse(valore.isEmpty());

		} catch (ConfigurationNotFoundException e) {

			Assert.fail(
					String.format(
							"La configurazione %s deve essere impostata",
							chiaveDiConfigurazione
					)
			);
		}

		try {

			String valore = configurazioneServiceSpy.readValue(chiaveDiConfigurazione, false);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(2)).readValue(chiaveDiConfigurazione, true);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readValue(chiaveDiConfigurazione, false);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(0)).readValue(chiaveDiConfigurazione);

			// Non devo leggere dalla cache
			Mockito.verify(configurazioneServiceSpy, Mockito.times(2)).readFromCache(chiaveDiConfigurazione);

			// Devo leggere dal database
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readFromDatabase(chiaveDiConfigurazione);
			Assert.assertNotNull(valore);
			Assert.assertFalse(valore.isEmpty());

			valore = configurazioneServiceSpy.readValue(chiaveDiConfigurazione, true);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(3)).readValue(chiaveDiConfigurazione, true);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readValue(chiaveDiConfigurazione, false);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(0)).readValue(chiaveDiConfigurazione);

			// Non devo leggere dalla cache
			Mockito.verify(configurazioneServiceSpy, Mockito.times(3)).readFromCache(chiaveDiConfigurazione);

			// Devo leggere da database
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readFromDatabase(chiaveDiConfigurazione);
			Assert.assertNotNull(valore);
			Assert.assertFalse(valore.isEmpty());

		} catch (ConfigurationNotFoundException e) {

			Assert.fail(
					String.format(
							"La configurazione %s deve essere impostata",
							chiaveDiConfigurazione
					)
			);
		}

		try {

			configurazioneServiceSpy.readValue(null, false);
			Assert.fail("La ricerca di un valore con chiave configurazione null deve tirare eccezione");
		} catch (Exception e) {

			Assert.assertTrue(e instanceof ConfigurationNotFoundException);
			Assert.assertEquals("Chiave di configurazione null", e.getMessage());
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readValue(null, false);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(0)).readValue(null, true);
		}

		try {

			configurazioneServiceSpy.readValue(null, true);
			Assert.fail("La ricerca di un valore con chiave configurazione null deve tirare eccezione");
		} catch (Exception e) {

			Assert.assertTrue(e instanceof ConfigurationNotFoundException);
			Assert.assertEquals("Chiave di configurazione null", e.getMessage());
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readValue(null, false);
			Mockito.verify(configurazioneServiceSpy, Mockito.times(1)).readValue(null, true);
		}
	}

	@Test
	public void readFromCache() {

		String value = configurazioneService.readFromCache(ChiaveConfigurazioneEnum.CONTEXT_PATH);
		Assert.assertFalse(value.isEmpty());

		try {

			configurazioneService.readFromCache(null);
			Assert.fail("La lettura di una configurazione null deve restituire errore");
		} catch (Exception e) {

			Assert.assertTrue(e instanceof NullPointerException);
			Assert.assertEquals("Impossibile leggere una chiave di configurazione null", e.getMessage());
		}
	}

	@Test
	public void readFromDatabase() {

		String value = configurazioneService.readFromDatabase(ChiaveConfigurazioneEnum.CONTEXT_PATH);
		Assert.assertFalse(value.isEmpty());

		try {

			configurazioneService.readFromDatabase(null);
			Assert.fail("La lettura di una configurazione null deve restituire errore");
		} catch (Exception e) {

			Assert.assertTrue(e instanceof NullPointerException);
			Assert.assertEquals("Impossibile leggere una chiave di configurazione null", e.getMessage());
		}
	}
}