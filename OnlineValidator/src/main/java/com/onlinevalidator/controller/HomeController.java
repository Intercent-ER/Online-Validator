package com.onlinevalidator.controller;

import com.onlinevalidator.model.OvRappresentazione;
import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.service.impl.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

	@Autowired
	private ValidatorService validatorService;

	@RequestMapping("/")
	public ModelAndView index() {

		return new ModelAndView("index");
	}

	@ModelAttribute("tipoDocumento")
	public List<OvTipoDocumento> getAllTipoDocumento() {
		return validatorService.filtraTuttiITipiDocumento();
	}
        
        @ModelAttribute("rappresentazione")
	public List<OvRappresentazione> getRappresentazione() {
		return validatorService.filtraRappresentazione();
	}

}
