package com.onlinevalidator.service.impl;

import com.onlinevalidator.dto.SessionFilterStorage;
import com.onlinevalidator.service.SessionCacheInterface;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author Manuel Gozzi
 */
@Service
public class SessionCacheService implements SessionCacheInterface {

	private HashMap<String, SessionFilterStorage> sessionCache = new HashMap<>();

	@Override
	public void addToStorage(String sessionId, SessionFilterStorage sessionFilterStorage) {

		sessionCache.put(sessionId, sessionFilterStorage);
		updateCache();
	}

	@Override
	public SessionFilterStorage queryStorage(String sessionId) {

		SessionFilterStorage optionalSessionFilterStorage = sessionCache.get(sessionId);
		if (optionalSessionFilterStorage != null) {
			sessionCache.remove(sessionId);
		}

		updateCache();
		return optionalSessionFilterStorage;
	}

	private void updateCache() {

		HashMap<String, SessionFilterStorage> newMap = new HashMap<>();
		for (String key : sessionCache.keySet()) {
			SessionFilterStorage sessionFilterStorage = sessionCache.get(key);
			if (!sessionFilterStorage.isExpired()) {
				newMap.put(key, sessionFilterStorage);
			}
		}
		this.sessionCache = newMap;
	}
}
