package com.onlinevalidator.exception.rendering;

/**
 * @author Manuel Gozzi
 */
public class XmlRenderingException extends RenderingException {

	public XmlRenderingException() {
	}

	public XmlRenderingException(String message) {
		super(message);
	}

	public XmlRenderingException(String message, Throwable cause) {
		super(message, cause);
	}

	public XmlRenderingException(Throwable cause) {
		super(cause);
	}

	public XmlRenderingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
