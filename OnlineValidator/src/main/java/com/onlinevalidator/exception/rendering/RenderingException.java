package com.onlinevalidator.exception.rendering;

/**
 * @author Manuel Gozzi
 */
public class RenderingException extends Exception {

	public RenderingException() {
	}

	public RenderingException(String message) {
		super(message);
	}

	public RenderingException(String message, Throwable cause) {
		super(message, cause);
	}

	public RenderingException(Throwable cause) {
		super(cause);
	}

	public RenderingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
