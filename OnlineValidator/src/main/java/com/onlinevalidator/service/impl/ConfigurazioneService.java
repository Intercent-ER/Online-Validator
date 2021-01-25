package com.onlinevalidator.service.impl;

import com.onlinevalidator.exception.ConfigurationNotFoundException;
import com.onlinevalidator.model.OvConfigurazione;
import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import com.onlinevalidator.repository.OvConfigurazioneJpaRepository;
import com.onlinevalidator.service.ConfigurazioneServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Manuel Gozzi
 */
@Service
public class ConfigurazioneService implements ConfigurazioneServiceInterface {

	private Map<ChiaveConfigurazioneEnum, String> cache;

	@Autowired
	private OvConfigurazioneJpaRepository configurazioneJpaRepository;

	@PostConstruct
	public void init() {
		logInfo("Inizializzazione cache configurazione");
		this.cache = new HashMap<>();
	}

	@Override
	public String readValue(ChiaveConfigurazioneEnum chiaveConfigurazioneEnum) {

		// Di default uso la cache
		return readValue(chiaveConfigurazioneEnum, true);
	}

	@Override
	public String readValue(ChiaveConfigurazioneEnum chiaveConfigurazioneEnum, boolean useCache) {

		// Se la chiave di configurazione è null, tiro eccezione
		if (chiaveConfigurazioneEnum == null) {
			logError("Chiave di configurazione null");
			throw new ConfigurationNotFoundException("Chiave di configurazione null");
		}

		// Se devo leggere dalla cache recupero il valore da lì, viceversa vado diretto sul database
		return useCache ?
				readFromCache(chiaveConfigurazioneEnum)
				: readFromDatabase(chiaveConfigurazioneEnum);
	}

	@Override
	public String readFromCache(ChiaveConfigurazioneEnum chiaveConfigurazioneEnum) {

		// Leggo dalla cache
		logInfo("Lettura configurazione {} da cache", chiaveConfigurazioneEnum.name());
		String value = cache.get(chiaveConfigurazioneEnum);

		// Se non ho trovato corrispondenze in cache, leggo sul database
		if (value == null) {

			logInfo("Chiave di configurazione {} non trovata in cache, si procede alla lettura sul database", chiaveConfigurazioneEnum.name());
			value = this.readFromDatabase(chiaveConfigurazioneEnum);

			// Inserisco in cache il valore recuperato
			cache.put(chiaveConfigurazioneEnum, value);
		}

		return value;
	}

	@Override
	public String readFromDatabase(ChiaveConfigurazioneEnum chiaveConfigurazioneEnum) {

		// Leggo la configurazione dal database
		logInfo("Lettura configurazione {} da database", chiaveConfigurazioneEnum.name());
		OvConfigurazione configurazione = configurazioneJpaRepository.findByCdChiaveConfigurazione(chiaveConfigurazioneEnum);

		// Se non ho trovato la configurazione, o se il suo valore è null, restituisco eccezione
		if (configurazione == null || configurazione.getCdValoreConfigurazione() == null) {
			throw ConfigurationNotFoundException.notFound(chiaveConfigurazioneEnum);
		}

		return configurazione.getCdValoreConfigurazione();
	}
}
