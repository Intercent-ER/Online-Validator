package com.onlinevalidator.exception;

/**
 * @author Manuel Gozzi
 */
public class VerificaReCaptchaException extends Exception {

	public VerificaReCaptchaException() {
	}

	public VerificaReCaptchaException(String message) {
		super(message);
	}

	public VerificaReCaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public VerificaReCaptchaException(Throwable cause) {
		super(cause);
	}

	public VerificaReCaptchaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
