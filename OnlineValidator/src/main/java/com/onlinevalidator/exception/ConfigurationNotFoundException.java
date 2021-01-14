package com.onlinevalidator.exception;

import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;

/**
 * @author Manuel Gozzi
 */
public class ConfigurationNotFoundException extends RuntimeException {

	public ConfigurationNotFoundException() {
	}

	public static synchronized ConfigurationNotFoundException notFound(ChiaveConfigurazioneEnum chiaveConfigurazioneEnum) {
		return new ConfigurationNotFoundException(
				String.format(
						"Non è stato possibile recuperare il valore della configurazione %s",
						chiaveConfigurazioneEnum != null ?
								chiaveConfigurazioneEnum.name()
								: "null"
				)
		);
	}

	public ConfigurationNotFoundException(String message) {
		super(message);
	}

	public ConfigurationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigurationNotFoundException(Throwable cause) {
		super(cause);
	}

	public ConfigurationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
