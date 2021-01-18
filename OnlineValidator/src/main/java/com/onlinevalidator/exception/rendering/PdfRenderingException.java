package com.onlinevalidator.exception.rendering;

/**
 * @author Manuel Gozzi
 */
public class PdfRenderingException extends RenderingException {

	public PdfRenderingException() {
	}

	public PdfRenderingException(String message) {
		super(message);
	}

	public PdfRenderingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PdfRenderingException(Throwable cause) {
		super(cause);
	}

	public PdfRenderingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
