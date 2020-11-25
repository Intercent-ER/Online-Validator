package com.onlinevalidator.controller;

import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.service.impl.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

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
	public @ResponseBody
	ModelAndView Validazione(@RequestParam("file") MultipartFile file,
							 @RequestParam(value = "id") int id) {

		ModelAndView paginaRisultato = new ModelAndView("result");
		paginaRisultato.addObject("message", validatorService.uploadFileHandler(file, id));
		return paginaRisultato;

	}

}
