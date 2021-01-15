package com.onlinevalidator.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.service.impl.ValidatorService;

@Controller
public class HomeController {
	
	@Autowired
	private ValidatorService validatorService;
	
	@RequestMapping("/")
	public ModelAndView fileUploader() {

		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}
	
	@ModelAttribute("tipoDocumento")
	public List<OvTipoDocumento> getAllTipoDocumento() throws SQLException {
		return validatorService.getAllEntity();
	}

}
