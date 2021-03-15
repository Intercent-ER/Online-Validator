package com.onlinevalidator.service;

import com.onlinevalidator.util.ApplicationLogger;

/**
 * @author Manuel Gozzi
 */
public interface VerifyRecaptchaInterface extends ApplicationLogger {

	/**
	 * Metodo che verifica l'integrità del captcha digitato dall'utente nella form.
	 *
	 * @param gRecaptchaResponse è il token relativo all'esecuzione del ReCaptcha di Google
	 * @return <code>true</code> nel caso in cui il token risulti conforme, <code>false</code> altrimenti
	 */
	boolean verify(String gRecaptchaResponse);
}
