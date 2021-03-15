package com.onlinevalidator.service;

import com.onlinevalidator.dto.SessionFilterStorage;
import com.onlinevalidator.util.ApplicationLogger;

/**
 * @author Manuel Gozzi
 */
public interface SessionCacheInterface extends ApplicationLogger {

	void addToStorage(String sessionId, SessionFilterStorage sessionFilterStorage);

	SessionFilterStorage queryStorage(String sessionId);
}
