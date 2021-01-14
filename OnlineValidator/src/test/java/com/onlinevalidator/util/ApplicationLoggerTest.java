package com.onlinevalidator.util;

import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * @author Manuel Gozzi
 */
public class ApplicationLoggerTest implements ApplicationLogger {

	@Test
	public void testlogInfo() {

		try {
			logInfo("messaggio");
			logInfo(null);
			logInfo("messaggio {}", new Object());
			String nullObject = null;
			logInfo("messaggio {}", nullObject);
		} catch (Exception e) {
			fail();
		}

	}

	@Test
	public void testlogError() {

		try {
			logError("messaggio");
			logError(null);
			logError("messaggio {}", new Object());
			String nullObject = null;
			logError("messaggio {}", nullObject);
		} catch (Exception e) {
			fail();
		}

	}

	@Test
	public void testlogWarn() {

		try {
			logWarn("messaggio");
			logWarn(null);
			logWarn("messaggio {}", new Object());
			String nullObject = null;
			logWarn("messaggio {}", nullObject);
		} catch (Exception e) {
			fail();
		}

	}

	@Test
	public void testlogDebug() {

		try {
			logDebug("messaggio");
			logDebug(null);
			logDebug("messaggio {}", new Object());
			String nullObject = null;
			logDebug("messaggio {}", nullObject);
		} catch (Exception e) {
			fail();
		}

	}

}