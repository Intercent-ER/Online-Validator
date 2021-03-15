package com.onlinevalidator.controller;

import com.google.gson.GsonBuilder;
import com.onlinevalidator.dto.SessionFilterStorage;
import com.onlinevalidator.service.RappresentazioneServiceInterface;
import com.onlinevalidator.service.SessionCacheInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author Manuel Gozzi
 */
@Controller
@RequestMapping("/ajax")
public class AjaxController {

	private Logger logger = LoggerFactory.getLogger(AjaxController.class);

	@Autowired
	private RappresentazioneServiceInterface rappresentazioneService;

	@Autowired
	private SessionCacheInterface sessionCache;

	@RequestMapping(value = "/displayRepresentations")
	public @ResponseBody
	String displayRappresentazione(@RequestParam("idTipoDocumento") int idTipoDocumento) {
		return new GsonBuilder()
				.setPrettyPrinting()
				.create()
				.toJson(
						rappresentazioneService.filtraRappresentazioniPerTipoDocumento(
								idTipoDocumento
						)
				);
	}

	@RequestMapping(value = "/prefillFilters")
	public @ResponseBody
	String prefillFilters(HttpSession session) {

		try {

			SessionFilterStorage sessionFilterStorage = sessionCache.queryStorage(session.getId());

			return new GsonBuilder()
					.setPrettyPrinting()
					.create()
					.toJson(sessionFilterStorage != null ?
							sessionFilterStorage
							: new SessionFilterStorage());
		} catch (Exception e) {

			logger.error("Si è verificato un errore: {}", e.getMessage(), e);
			throw e;
		}
	}

}
