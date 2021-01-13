package com.onlinevalidator.controller;

import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.pojo.ValidationReport;
import com.onlinevalidator.service.impl.ValidatorService;
import com.onlinevalidator.util.CostantiWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ResultController {

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

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView Validazione(@RequestParam("file") MultipartFile file,
							 @RequestParam(value = "id") int id) {

		ModelAndView paginaRisultato = new ModelAndView("result");
		try {

			ValidationReport risultatoValidazione = validatorService.effettuaValidazione(file.getBytes(), validatorService.getTipodocumentoById(id));
			paginaRisultato.addObject("contieneErroriFatali", Boolean.toString(risultatoValidazione.contieneErrori()));
			paginaRisultato.addObject(CostantiWeb.RESULT_CONTROLLER_ASSERT_VALIDAZIONE, risultatoValidazione.getErroriDiValidazione());

		} catch (IOException e) {
			e.printStackTrace();
		}
//		paginaRisultato.addObject("message", file.getBytes());

		return paginaRisultato;

	}

}
