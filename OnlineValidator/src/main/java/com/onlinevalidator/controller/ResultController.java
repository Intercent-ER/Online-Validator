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

import java.util.List;

@Controller
public class ResultController {

	private static final Logger logger = LoggerFactory.getLogger(ResultController.class);

	@Autowired
	private ValidatorService validatorService;

	@RequestMapping("/")
	public ModelAndView fileUploader() {
		return new ModelAndView("index");
	}

	@ModelAttribute("tipoDocumento")
	public List<OvTipoDocumento> getAllTipoDocumento() {
		return validatorService.filtraTuttiITipiDocumento();
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView validazione(@RequestParam("file") MultipartFile file,
							 @RequestParam(value = "id") int id) {

		ModelAndView paginaRisultato = new ModelAndView("result");
		try {

			// Eseguo la validazione
			logger.info("Ricevuta richiesta di validazione per tipo documento {}", id);
			ValidationReport risultatoValidazione = validatorService.effettuaValidazione(file.getBytes(), validatorService.getOvTipoDocumentoById(id));
			if (risultatoValidazione == null) {
				throw new NullPointerException("Nessun risultato di validazione consultabile");
			}

			// Logging
			logger.info(
					"Risultato di validazione: {}",
					!risultatoValidazione.contieneErrori()
			);

			// Aggiunta dei risultati
			paginaRisultato.addObject(
					CostantiWeb.RESULT_CONTROLLER_ASSERT_VALIDAZIONE,
					risultatoValidazione.getErroriDiValidazione()
			);
			paginaRisultato.addObject(
					CostantiWeb.RESULT_CONTROLLER_ERRORE_XSD,
					risultatoValidazione.getDescrizioneErroreXsd()
			);

		} catch (Exception e) {

			// Logging dell'errore e gestione del messaggio
			logger.error("Si è verificato un errore durante la validazione: {}", e.getMessage(), e);
			paginaRisultato.addObject("errorMessage", e.getMessage());
		}

		return paginaRisultato;
	}

}
