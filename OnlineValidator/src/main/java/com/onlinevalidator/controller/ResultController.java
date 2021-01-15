package com.onlinevalidator.controller;

<<<<<<< Updated upstream
import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.pojo.ValidationReport;
import com.onlinevalidator.service.impl.ValidatorService;
import com.onlinevalidator.util.CostantiWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
=======
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

>>>>>>> Stashed changes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

<<<<<<< Updated upstream
import java.util.List;
=======
import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.pojo.ValidationReport;
import com.onlinevalidator.service.impl.ValidatorService;
import com.onlinevalidator.util.CostantiWeb;
import com.onlinevalidator.util.FileUtil;
>>>>>>> Stashed changes

@Controller
public class ResultController {

	private static final Logger logger = LoggerFactory.getLogger(ResultController.class);

	@Autowired
	private ValidatorService validatorService;

<<<<<<< Updated upstream
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
=======
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody ModelAndView Validazione(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "id") int id) {
>>>>>>> Stashed changes

		ModelAndView paginaRisultato = new ModelAndView("result");
		try {

<<<<<<< Updated upstream
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
=======
			ValidationReport risultatoValidazione = validatorService.effettuaValidazione(file.getBytes(),
					validatorService.getTipodocumentoById(id));
			paginaRisultato.addObject("contieneErroriFatali", Boolean.toString(risultatoValidazione.contieneErrori()));
			paginaRisultato.addObject(CostantiWeb.RESULT_CONTROLLER_ASSERT_VALIDAZIONE,
					risultatoValidazione.getErroriDiValidazione());
>>>>>>> Stashed changes

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

	private String convertiInXml(ValidationReport report) {
		
		return null; // TODO da fare
	}

	@RequestMapping(value = "/dadecidere", method = RequestMethod.GET)
	public void stampaXml(HttpServletResponse response, @RequestParam("reportValidazione") ValidationReport report) {
		String xmlConvertito = convertiInXml(report);
		try {
			FileUtil.outputFile(response, new ByteArrayInputStream(xmlConvertito.getBytes(StandardCharsets.UTF_8)),
					"esito-validazione.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
