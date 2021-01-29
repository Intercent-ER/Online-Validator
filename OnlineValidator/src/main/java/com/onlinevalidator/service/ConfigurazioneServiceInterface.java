package com.onlinevalidator.service;

import com.onlinevalidator.exception.ConfigurationNotFoundException;
import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import com.onlinevalidator.util.ApplicationLogger;

/**
 * @author Manuel Gozzi
 */
public interface ConfigurazioneServiceInterface extends ApplicationLogger {

	/**
	 * Legge un valore di configurazione (utilizzando la cache {@link #readValue(ChiaveConfigurazioneEnum, boolean)}).
	 *
	 * @param chiaveConfigurazioneEnum è la chiave di configurazione
	 * @return il valore della configurazione richiesta
	 * @throws ConfigurationNotFoundException nel caso in cui la chiave fornita sia null, oppure non esista la
	 *                                        configurazione richiesta
	 */
	String readValue(ChiaveConfigurazioneEnum chiaveConfigurazioneEnum) throws ConfigurationNotFoundException;

	/**
	 * Legge un valore di configurazione; se l'argomento "useCache" è <code>true</code>, allora si procede a ricercare
	 * il valore richiesto prima in cache (se non presente, lo si recupera e si inserisce il risultato in cache).
	 *
	 * @param chiaveConfigurazioneEnum è la chiave di configurazione
	 * @param useCache                 se <code>true</code> utilizza il meccanismo di cache, viceversa legge da database
	 * @return il valore della configurazione richiesta
	 * @throws ConfigurationNotFoundException nel caso in cui la chiave fornita sia null, oppure non esista la
	 *                                        configurazione richiesta
	 */
	String readValue(ChiaveConfigurazioneEnum chiaveConfigurazioneEnum, boolean useCache) throws ConfigurationNotFoundException;

	/**
	 * Legge un valore di configurazione dalla cache; aggiorna la cache se necessario.
	 *
	 * @param chiaveConfigurazioneEnum è la chiave di configurazione
	 * @return il valore di configurazione
	 */
	String readFromCache(ChiaveConfigurazioneEnum chiaveConfigurazioneEnum);

	/**
	 * Legge un valore di configurazione dal database.
	 *
	 * @param chiaveConfigurazioneEnum è la chiave di configurazione
	 * @return il valore recuperato
	 */
	String readFromDatabase(ChiaveConfigurazioneEnum chiaveConfigurazioneEnum);
}
