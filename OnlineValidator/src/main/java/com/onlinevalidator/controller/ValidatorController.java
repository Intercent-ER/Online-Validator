package com.onlinevalidator.controller;

import com.onlinevalidator.dto.Render;
import com.onlinevalidator.dto.ValidationReport;
import com.onlinevalidator.pojo.TipoRenderingEnum;
import com.onlinevalidator.service.RenderingServiceInterface;
import com.onlinevalidator.service.impl.ValidatorService;
import com.onlinevalidator.util.CostantiWeb;
import com.onlinevalidator.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

@Controller
public class ValidatorController {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorController.class);

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private RenderingServiceInterface renderingService;

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView uploadFile(@RequestParam("file") MultipartFile file,
							@RequestParam(value = "id") int id, HttpSession session) {

		ModelAndView paginaRisultato = new ModelAndView("result");
		try {

			// Eseguo la validazione
			logger.info("Ricevuta richiesta di validazione per tipo documento {}", id);
			String documentoString = new String(file.getBytes());
			ValidationReport risultatoValidazione = validatorService.effettuaValidazione(
					documentoString.getBytes(StandardCharsets.UTF_8),
					validatorService.getOvTipoDocumentoById(id)
			);
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
					CostantiWeb.RESULT_CONTROLLER_IS_VALIDO,
					risultatoValidazione.isValido()
			);
			paginaRisultato.addObject(
					CostantiWeb.RESULT_CONTROLLER_ERRORE_XSD,
					risultatoValidazione.getDescrizioneErroreXsd()
			);
			paginaRisultato.addObject(
					CostantiWeb.RESULT_CONTROLLER_DATA_VALIDAZIONE,
					new SimpleDateFormat(CostantiWeb.PATTERN_SIMPLE_DATE_FORMAT).format(risultatoValidazione.getDataDiGenerazione())
			);
			paginaRisultato.addObject(
					CostantiWeb.RESULT_CONTROLLER_RISULTATO_VALIDAZIONE,
					risultatoValidazione
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
	public void esportaRisultato(@RequestParam("tipoRendering") TipoRenderingEnum tipoRendering, HttpServletResponse response, HttpSession session) {
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
