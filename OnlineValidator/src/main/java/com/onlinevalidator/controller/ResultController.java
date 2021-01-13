package com.onlinevalidator.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.pojo.ValidationReport;
import com.onlinevalidator.service.impl.ValidatorService;

@Controller
public class ResultController {


	@Autowired
	ValidatorService validatorService;
    
	@Autowired
	ValidationReport validationReport;

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
		try {
			
			ValidationReport risultatoValidazione=validatorService.effettuaValidazione(file.getBytes(),validatorService.getTipodocumentoById(id));
			risultatoValidazione.contieneErrori();
			//TODO contiene errori è booleana , serve conferma i validazione o meno
			paginaRisultato.addObject("assertDiValidazione",validationReport.getErroriDiValidazione());
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		paginaRisultato.addObject("message", file.getBytes());
		
		return paginaRisultato;

	}

}
