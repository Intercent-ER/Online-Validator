package com.onlinevalidator.controller;

import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.pojo.ValidationReport;
import com.onlinevalidator.service.impl.ValidatorService;
import com.onlinevalidator.util.CostantiWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class ResultController {

	private static final Logger logger = LoggerFactory.getLogger(ResultController.class);

	@Autowired
	private ValidatorService validatorService;

	@RequestMapping("/")
	public ModelAndView fileUploader() {

		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}

	@ModelAttribute("tipoDocumento")
	public List<OvTipoDocumento> getAllTipoDocumento() {
		return validatorService.filtraTuttiITipiDocumento();
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView Validazione(@RequestParam("file") MultipartFile file,
							 @RequestParam(value = "id") int id) {

		ModelAndView paginaRisultato = new ModelAndView("result");
		try {

			ValidationReport risultatoValidazione = validatorService.effettuaValidazione(file.getBytes(), validatorService.getOvTipoDocumentoById(id));

			paginaRisultato.addObject("contieneErroriFatali", Boolean.toString(risultatoValidazione.contieneErrori()));
			paginaRisultato.addObject(CostantiWeb.RESULT_CONTROLLER_ASSERT_VALIDAZIONE, risultatoValidazione.getErroriDiValidazione());
			paginaRisultato.addObject("erroreXsd", risultatoValidazione.getDescrizioneErroreXsd());

		} catch (IOException e) {
			logger.error("Si è verificato un errore durante la validazione: {}", e.getMessage(), e);
			paginaRisultato.addObject("errorMessage", e.getMessage());
		}

		return paginaRisultato;

	}

}
