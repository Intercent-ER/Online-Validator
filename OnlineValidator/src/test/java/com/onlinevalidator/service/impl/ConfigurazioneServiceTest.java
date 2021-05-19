package com.onlinevalidator.service.impl;

import com.onlinevalidator.exception.ConfigurationNotFoundException;
import com.onlinevalidator.model.OvConfigurazione;
import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import com.onlinevalidator.repository.OvConfigurazioneJpaRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Manuel Gozzi
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfigurazioneServiceTest {

	// Chiave di configurazione utilizzata all'interno degli unit test
	private static final ChiaveConfigurazioneEnum CONTEXT_PATH = ChiaveConfigurazioneEnum.CONTEXT_PATH;

	@Before
	public void initMocks() {

		MockitoAnnotations.initMocks(ConfigurazioneServiceTest.class);
	}

	@Test
	public void init() {

		try {

			ConfigurazioneService configurazioneService = spy(new ConfigurazioneService(null));

			// Verifico che l'inizializzazione non generi errori
			configurazioneService.init();
			verify(configurazioneService, times(1))
					.logInfo(anyString());
			verify(configurazioneService, times(1))
					.init();
			verifyNoMoreInteractions(configurazioneService);
		} catch (Exception e) {

			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void readValue() {

		// Predispongo il mock dell'oggetto di configurazione
		OvConfigurazione ovConfigurazione = mock(OvConfigurazione.class);
		when(ovConfigurazione.getCdValoreConfigurazione())
				.thenReturn("localhost");

		// Costruisco il mock del repository
		OvConfigurazioneJpaRepository ovConfigurazioneJpaRepository = mock(OvConfigurazioneJpaRepository.class);
		when(ovConfigurazioneJpaRepository.findByCdChiaveConfigurazione(CONTEXT_PATH))
				.thenReturn(ovConfigurazione);

		// Spio il service oggetto di test
		ConfigurazioneService configurazioneService = spy(new ConfigurazioneService(ovConfigurazioneJpaRepository));

		// Eseguo il metodo oggetto di test
		String value = configurazioneService.readValue(CONTEXT_PATH);

		// Verifico il suo comportamento
		verify(configurazioneService, times(0))
				.logError(anyString());
		verify(configurazioneService, times(1))
				.readValue(CONTEXT_PATH);
		verify(configurazioneService, times(1))
				.readValue(ChiaveConfigurazioneEnum.CONTEXT_PATH, true);
		verify(configurazioneService, times(0))
				.readValue(ChiaveConfigurazioneEnum.CONTEXT_PATH, false);
		verify(configurazioneService, times(1))
				.readFromCache(ChiaveConfigurazioneEnum.CONTEXT_PATH);
		verify(configurazioneService, times(1))
				.readFromDatabase(ChiaveConfigurazioneEnum.CONTEXT_PATH);
		assertEquals("localhost", value);

		// Eseguo il secondo metodo (overload)
		String secondValue = configurazioneService.readValue(CONTEXT_PATH, false);

		// Verifico il suo comportamento, tenendo conto dell'esecuzione precedente
		verify(configurazioneService, times(0))
				.logError(anyString());
		verify(configurazioneService, times(1))
				.readValue(CONTEXT_PATH);
		verify(configurazioneService, times(1))
				.readValue(CONTEXT_PATH, true);
		verify(configurazioneService, times(1))
				.readValue(CONTEXT_PATH, false);
		verify(configurazioneService, times(1))
				.readFromCache(ChiaveConfigurazioneEnum.CONTEXT_PATH);
		verify(configurazioneService, times(2))
				.readFromDatabase(ChiaveConfigurazioneEnum.CONTEXT_PATH);
		assertEquals("localhost", secondValue);

		try {

			configurazioneService.readValue(null);
			fail("La lettura di una configurazione null deve restituire errore");
		} catch (Exception e) {

			// Verifico lo stato dell'eccezione restituita
			assertTrue(e instanceof ConfigurationNotFoundException);
			verify(configurazioneService, times(1))
					.logError("Chiave di configurazione null");
			verify(configurazioneService, times(1))
					.readValue(CONTEXT_PATH);
			verify(configurazioneService, times(1))
					.readValue(null);
			verify(configurazioneService, times(1))
					.readValue(CONTEXT_PATH, true);
			verify(configurazioneService, times(1))
					.readValue(CONTEXT_PATH, false);
			verify(configurazioneService, times(1))
					.readFromCache(ChiaveConfigurazioneEnum.CONTEXT_PATH);
			verify(configurazioneService, times(2))
					.readFromDatabase(ChiaveConfigurazioneEnum.CONTEXT_PATH);
		}

		try {

			configurazioneService.readValue(null, true);
			fail("La lettura di una configurazione null deve restituire errore");
		} catch (Exception e) {

			// Verifico lo stato dell'eccezione restituita
			assertTrue(e instanceof ConfigurationNotFoundException);
			verify(configurazioneService, times(2))
					.logError("Chiave di configurazione null");
			verify(configurazioneService, times(1))
					.readValue(CONTEXT_PATH);
			verify(configurazioneService, times(1))
					.readValue(null);
			verify(configurazioneService, times(1))
					.readValue(CONTEXT_PATH, true);
			verify(configurazioneService, times(2))
					.readValue(null, true);
			verify(configurazioneService, times(1))
					.readValue(CONTEXT_PATH, false);
			verify(configurazioneService, times(1))
					.readFromCache(ChiaveConfigurazioneEnum.CONTEXT_PATH);
			verify(configurazioneService, times(2))
					.readFromDatabase(ChiaveConfigurazioneEnum.CONTEXT_PATH);
		}

		try {

			configurazioneService.readValue(null, false);
			fail("La lettura di una configurazione null deve restituire errore");
		} catch (Exception e) {

			// Verifico lo stato dell'eccezione restituita
			assertTrue(e instanceof ConfigurationNotFoundException);
			verify(configurazioneService, times(3))
					.logError("Chiave di configurazione null");
			verify(configurazioneService, times(1))
					.readValue(CONTEXT_PATH);
			verify(configurazioneService, times(1))
					.readValue(null);
			verify(configurazioneService, times(1))
					.readValue(CONTEXT_PATH, true);
			verify(configurazioneService, times(2))
					.readValue(null, true);
			verify(configurazioneService, times(1))
					.readValue(null, false);
			verify(configurazioneService, times(1))
					.readValue(CONTEXT_PATH, false);
			verify(configurazioneService, times(1))
					.readFromCache(ChiaveConfigurazioneEnum.CONTEXT_PATH);
			verify(configurazioneService, times(2))
					.readFromDatabase(ChiaveConfigurazioneEnum.CONTEXT_PATH);
		}
	}

	@Test
	public void readFromCache() {

		// Predispongo il mock per l'oggetto di configurazione
		OvConfigurazione ovConfigurazione = mock(OvConfigurazione.class);
		when(ovConfigurazione.getCdValoreConfigurazione()).thenReturn("localhost");

		// Predispongo il mock del repository
		OvConfigurazioneJpaRepository ovConfigurazioneJpaRepository = mock(OvConfigurazioneJpaRepository.class);
		when(ovConfigurazioneJpaRepository.findByCdChiaveConfigurazione(CONTEXT_PATH))
				.thenReturn(ovConfigurazione);

		// Spio il service oggetto di test
		ConfigurazioneService configurazioneService = spy(new ConfigurazioneService(ovConfigurazioneJpaRepository));

		// Eseguo il metodo oggetto di test
		String value = configurazioneService.readFromCache(CONTEXT_PATH);

		// Verifico il suo comportamento
		verify(configurazioneService, times(1))
				.init();
		verify(ovConfigurazioneJpaRepository, times(1))
				.findByCdChiaveConfigurazione(CONTEXT_PATH);
		verify(configurazioneService, times(1))
				.readFromDatabase(CONTEXT_PATH);
		assertEquals("localhost", value);

		try {

			// Verifico il comportamento del metodo oggetto di test a fronte della ricezione di un input null
			configurazioneService.readFromCache(null);
			fail("La lettura di una configurazione null deve restituire errore");
		} catch (Exception e) {

			// Verifico lo stato dell'eccezione restituita
			assertTrue(e instanceof NullPointerException);
			assertEquals("Impossibile leggere una chiave di configurazione null", e.getMessage());
			verify(ovConfigurazioneJpaRepository, times(0))
					.findByCdChiaveConfigurazione(null);
		}
	}

	@Test
	public void readFromDatabase() {

		// Creo il mock della configurazione
		OvConfigurazione ovConfigurazione = mock(OvConfigurazione.class);
		when(ovConfigurazione.getCdValoreConfigurazione()).thenReturn("localhost");

		// Creo il mock del repository
		OvConfigurazioneJpaRepository ovConfigurazioneJpaRepository = mock(OvConfigurazioneJpaRepository.class);

		// Piloto l'esecuzione della query facendo restituire il mock preposto
		when(ovConfigurazioneJpaRepository.findByCdChiaveConfigurazione(CONTEXT_PATH))
				.thenReturn(ovConfigurazione);

		// Spio il service di configurazione
		ConfigurazioneService configurazioneService = spy(new ConfigurazioneService(ovConfigurazioneJpaRepository));

		// Eseguo il metodo oggetto di test
		String value = configurazioneService.readFromDatabase(CONTEXT_PATH);

		// Verifico le interazioni
		verify(configurazioneService, times(0))
				.init();
		verify(configurazioneService, times(1))
				.readFromDatabase(CONTEXT_PATH);
		verify(ovConfigurazioneJpaRepository, times(1))
				.findByCdChiaveConfigurazione(CONTEXT_PATH);
		assertEquals("localhost", value);

		try {

			// Verifico il comportamento del metodo alla ricezione di un parametro null
			configurazioneService.readFromDatabase(null);
			fail("La lettura di una configurazione null deve restituire errore");
		} catch (Exception e) {

			// Verifico lo stato dell'eccezione
			assertTrue(e instanceof NullPointerException);
			assertEquals("Impossibile leggere una chiave di configurazione null", e.getMessage());
			verify(ovConfigurazioneJpaRepository, times(0))
					.findByCdChiaveConfigurazione(null);
		}

		// Modifico i precedenti mock (configurazione e repository)
		when(ovConfigurazione.getCdValoreConfigurazione()).thenReturn(null);
		when(ovConfigurazioneJpaRepository.findByCdChiaveConfigurazione(CONTEXT_PATH))
				.thenReturn(ovConfigurazione);

		try {

			// Eseguo il metodo d'interesse
			configurazioneService.readValue(CONTEXT_PATH);
			fail("La lettura di un oggetto di configurazione con valore null deve restituire errore");
		} catch (Exception e) {

			// Verifico lo stato dell'eccezione restituita
			assertTrue(e instanceof ConfigurationNotFoundException);
			assertTrue(e.getMessage().contains(CONTEXT_PATH.name()));
			verify(configurazioneService, times(2))
					.readFromDatabase(CONTEXT_PATH);
			verify(ovConfigurazioneJpaRepository, times(2))
					.findByCdChiaveConfigurazione(CONTEXT_PATH);
		}
	}
}