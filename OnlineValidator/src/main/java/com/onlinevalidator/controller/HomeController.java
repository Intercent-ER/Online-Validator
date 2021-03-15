package com.onlinevalidator.controller;

import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.service.impl.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class HomeController {

	@Autowired
	private ValidatorService validatorService;

	private List<OvTipoDocumento> tipiDocumento;

	@PostConstruct
	public void init() {
		this.tipiDocumento = validatorService.filtraTuttiITipiDocumento();
	}

	@RequestMapping("/")
	public ModelAndView index() {

		return new ModelAndView("index");
	}

	@ModelAttribute("tipoDocumento")
	public List<OvTipoDocumento> getAllTipoDocumento() {
		return tipiDocumento;
	}

}
