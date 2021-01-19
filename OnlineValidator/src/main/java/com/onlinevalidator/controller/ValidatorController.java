package com.onlinevalidator.controller;

import com.onlinevalidator.dto.Render;
import com.onlinevalidator.dto.ValidationReport;
import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.pojo.TipoRenderingEnum;
import com.onlinevalidator.service.RenderingServiceInterface;
import com.onlinevalidator.service.impl.ValidatorService;
import com.onlinevalidator.util.CostantiWeb;
import com.onlinevalidator.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class ValidatorController {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorController.class);

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private RenderingServiceInterface renderingService;

	@ModelAttribute("tipoDocumento")
	public List<OvTipoDocumento> getAllTipoDocumento() {
		return validatorService.filtraTuttiITipiDocumento();
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView validazione(@RequestParam("file") MultipartFile file,
							 @RequestParam(value = "id") int id, HttpSession session) {

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
			paginaRisultato.addObject(
					CostantiWeb.RESULT_CONTROLLER_DATA_VALIDAZIONE,
					new SimpleDateFormat(CostantiWeb.PATTERN_SIMPLE_DATE_FORMAT).format(risultatoValidazione.getDataDiGenerazione())
			);
			session.setAttribute(
					CostantiWeb.RESULT_CONTROLLER_RISULTATO_VALIDAZIONE,
					risultatoValidazione
			);

		} catch (Exception e) {

			// Logging dell'errore e gestione del messaggio
			logger.error("Si è verificato un errore durante la validazione: {}", e.getMessage(), e);
			paginaRisultato.addObject("errorMessage", e.getMessage());
		}

		return paginaRisultato;
	}

	@RequestMapping(value = "/esportaRisultato", method = RequestMethod.GET)
	public void stampaXml(@RequestParam("tipoRendering") TipoRenderingEnum tipoRendering, HttpServletResponse response, HttpSession session) {
		try {

			ValidationReport report = (ValidationReport) session.getAttribute(
					CostantiWeb.RESULT_CONTROLLER_RISULTATO_VALIDAZIONE
			);

			// Eseguo il rendering
			Render render = renderingService.render(report, tipoRendering);

			// Salvo l'output
			FileUtil.outputFile(
					response,
					new ByteArrayInputStream(
							render.getFile()
					),
					render.getFileName()
			);
		} catch (Exception e) {
			logger.error("Si è verificato un errore durante l'esportazione: {}", e.getMessage(), e);
		}
	}

}
