package com.onlinevalidator.util;

import org.slf4j.LoggerFactory;

/**
 * @author Manuel Gozzi
 */
public interface ApplicationLogger {

	default void logInfo(String message) {
		LoggerFactory.getLogger(this.getClass()).info(message);
	}

	default void logInfo(String message, Object... objects) {
		LoggerFactory.getLogger(this.getClass()).info(message, objects);
	}

	default void logError(String message) {
		LoggerFactory.getLogger(this.getClass()).error(message);
	}

	default void logError(String message, Object... objects) {
		LoggerFactory.getLogger(this.getClass()).error(message, objects);
	}

	default void logWarn(String message) {
		LoggerFactory.getLogger(this.getClass()).warn(message);
	}

	default void logWarn(String message, Object... objects) {
		LoggerFactory.getLogger(this.getClass()).warn(message, objects);
	}

	default void logDebug(String message) {
		LoggerFactory.getLogger(this.getClass()).debug(message);
	}

	default void logDebug(String message, Object... objects) {
		LoggerFactory.getLogger(this.getClass()).debug(message, objects);
	}

}

