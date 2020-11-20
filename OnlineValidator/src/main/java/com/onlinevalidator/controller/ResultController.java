package com.onlinevalidator.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.onlinevalidator.model.TipoFileEnum;
import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.model.Validatore;
import com.onlinevalidator.services.ValidatorService;
import com.onlinevalidator.utils.FormatCheckerInterface;

@Controller
public class ResultController {

	
    @Autowired
	ValidatorService validatorService;


	@RequestMapping("/")
	public ModelAndView fileUploader() {

		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;

	}

	@ModelAttribute("tipoDocumento")
	public List<Tipodocumento> getAllTipoDocumento() throws SQLException {
		return validatorService.getAllEntity();

	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody ModelAndView Validazione(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "id") int id) {

		ModelAndView paginaRisultato = new ModelAndView("result");
		paginaRisultato.addObject("message",validatorService.uploadFileHandler(file, id));
		return paginaRisultato;

	}

}
