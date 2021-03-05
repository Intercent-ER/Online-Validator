package com.onlinevalidator.controller;

import com.google.gson.GsonBuilder;
import com.onlinevalidator.service.RappresentazioneServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Manuel Gozzi
 */
@Controller
@RequestMapping("/ajax")
public class AjaxController {

	@Autowired
	private RappresentazioneServiceInterface rappresentazioneService;

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
}
