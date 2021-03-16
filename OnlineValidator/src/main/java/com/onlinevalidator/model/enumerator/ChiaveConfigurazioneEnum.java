package com.onlinevalidator.model.enumerator;

/**
 * @author Manuel Gozzi
 */
public enum ChiaveConfigurazioneEnum {

	CONTEXT_PATH, G_RECAPTCHA_SECRET, G_RECAPTCHA_URL, G_RECAPTCHA_USER_AGENT;

	@Override
	public String toString() {
		return name();
	}
}
