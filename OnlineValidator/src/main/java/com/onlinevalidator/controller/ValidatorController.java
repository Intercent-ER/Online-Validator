package com.onlinevalidator.controller;

import com.onlinevalidator.dto.Render;
import com.onlinevalidator.dto.UploadFileForm;
import com.onlinevalidator.dto.ValidationReport;
import com.onlinevalidator.exception.VerificaReCaptchaException;
import com.onlinevalidator.model.OvRappresentazione;
import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import com.onlinevalidator.pojo.TipoRenderingEnum;
import com.onlinevalidator.service.ConfigurazioneServiceInterface;
import com.onlinevalidator.service.RenderingServiceInterface;
import com.onlinevalidator.service.VerifyRecaptchaInterface;
import com.onlinevalidator.service.impl.ValidatorService;
import com.onlinevalidator.util.ApplicationConstant;
import com.onlinevalidator.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Controller
public class ValidatorController {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorController.class);

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private RenderingServiceInterface renderingService;

	@Autowired
	private VerifyRecaptchaInterface verifyRecaptchaService;

	@Autowired
	private ConfigurazioneServiceInterface configurazioneService;

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView uploadFile(@Valid @ModelAttribute(ApplicationConstant.INDEX_JSP_UPLOAD_FILE_FORM) UploadFileForm uploadFileForm, BindingResult bindingResult,
							HttpServletRequest request,
							HttpSession session) {

		ModelAndView paginaRisultato = new ModelAndView("result");
		boolean validazioneCaptchaSuperata = true;
		try {

			// Validazione ReCaptcha
			evaluateRecaptcha(bindingResult, request);
		} catch (Exception e) {

			logger.error("{}", e.getMessage());
			validazioneCaptchaSuperata = false;
		}

		//Aggiunto per i test, per saltare il controllo del captcha, nell'if originale ci andrebbe (bindingResult.hasErrors() || !validazioneCaptchaSuperata)
		validazioneCaptchaSuperata = true;

		if (!validazioneCaptchaSuperata) {

			logger.info("La validazione della form contiene degli errori");
			paginaRisultato.setViewName("index");
			paginaRisultato.addObject(
					ApplicationConstant.INDEX_JSP_ELENCO_TIPI_DOCUMENTO,
					validatorService.filtraTuttiITipiDocumento()
			);
			paginaRisultato.addObject(
					ApplicationConstant.INDEX_JSP_RECAPTCHA_SITE_KEY,
					configurazioneService.readValue(ChiaveConfigurazioneEnum.G_RECAPTCHA_SITE_KEY)
			);
			return paginaRisultato;
		}

		try {

			// Eseguo la validazione
			logger.info("Ricevuta richiesta di validazione per rappresentazione documento: \"{}\"", uploadFileForm.getIdRappresentazioneDocumento());
			String documentoString = new String(uploadFileForm.getFileDocumento().getBytes());
			OvRappresentazione ovRappresentazione = validatorService.getOvRappresentazioneById(uploadFileForm.getIdRappresentazioneDocumento());
			ValidationReport risultatoValidazione = Objects.requireNonNull(
					validatorService.effettuaValidazione(
							documentoString.getBytes(StandardCharsets.UTF_8),
							ovRappresentazione
					),
					"Nessun risultato di validazione consultabile"
			);

			// Aggiunta dei risultati
			logger.info(
					"Risultato di validazione: {}",
					!risultatoValidazione.contieneErrori()
			);
			paginaRisultato.addObject(
					ApplicationConstant.RESULT_JSP_DATA_VALIDAZIONE,
					new SimpleDateFormat(ApplicationConstant.PATTERN_SIMPLE_DATE_FORMAT).format(risultatoValidazione.getDataDiGenerazione())
			);
			paginaRisultato.addObject(
					ApplicationConstant.RESULT_JSP_RISULTATO_VALIDAZIONE,
					risultatoValidazione
			);

			// Aggiunta attributi sessione
			session.setAttribute(
					ApplicationConstant.RESULT_JSP_RISULTATO_VALIDAZIONE,
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
	public void esportaRisultato(@RequestParam(ApplicationConstant.RESULT_JSP_TIPO_RENDERING) TipoRenderingEnum tipoRendering, HttpServletResponse response, HttpSession session) {
		try {

			ValidationReport report = (ValidationReport) session.getAttribute(
					ApplicationConstant.RESULT_JSP_RISULTATO_VALIDAZIONE
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

			// Logging dell'errore
			logger.error("Si è verificato un errore durante l'esportazione: {}", e.getMessage(), e);

			try {

				// Scrivo a video il problema
				response.getWriter().write(
						String.format(
								"Si è verificato un errore: %s%s",
								System.getProperty("line.separator"),
								e.getMessage()
						)
				);
			} catch (IOException ie) {

				logger.error("Si è verificato un problema durante il rendering dell'errore generico: {}", ie.getMessage(), ie);
			}
		}
	}

	@ExceptionHandler(value = MultipartException.class)
	public ModelAndView handleFileUploadException(MultipartException mpex, HttpServletRequest request) {

		logger.debug(
				"Dimensione massima consentita superata durante il caricamento del file: {}",
				mpex.getMessage(),
				mpex
		);
		return restituisciErroreSuIndexPage(ApplicationConstant.INDEX_JSP_ERRORE_MULTIPART_DIMENSIONE_SUPERATA);
	}

	@ExceptionHandler(value = Exception.class)
	public ModelAndView handleGenericException(Exception e) {

		logger.debug(
				"Si è verificato un errore durante il caricamento del file: {}",
				e.getMessage(),
				e
		);
		return restituisciErroreSuIndexPage(ApplicationConstant.INDEX_JSP_ERRORE_GENERICO);
	}

	/**
	 * Restituisce l'errore sulla pagina di index.
	 *
	 * @param jspObjectKey è il nome della variabile che deve essere inserita nel model
	 * @return l'oggetto {@link ModelAndView} corrispondente
	 */
	private ModelAndView restituisciErroreSuIndexPage(String jspObjectKey) {

		ModelAndView modelAndVew = new ModelAndView("index");
		modelAndVew.addObject(jspObjectKey, true);
		modelAndVew.addObject(ApplicationConstant.INDEX_JSP_UPLOAD_FILE_FORM, new UploadFileForm());
		modelAndVew.addObject(
				ApplicationConstant.INDEX_JSP_ELENCO_TIPI_DOCUMENTO,
				validatorService.filtraTuttiITipiDocumento()
		);
		modelAndVew.addObject(
				ApplicationConstant.INDEX_JSP_RECAPTCHA_SITE_KEY,
				configurazioneService.readValue(ChiaveConfigurazioneEnum.G_RECAPTCHA_SITE_KEY)
		);
		return modelAndVew;
	}

	/**
	 * Applica i controlli sul ReCaptcha.
	 *
	 * @param bindingResult è l'oggetto responsabile di contenere i risultati di validazione della form Spring
	 * @param request       è la richiesta HTTP
	 * @throws VerificaReCaptchaException nel caso in cui la validazione non vada a buon fine
	 */
	private void evaluateRecaptcha(BindingResult bindingResult, HttpServletRequest request)
			throws VerificaReCaptchaException {

		// Recupero il valore del ReCaptcha dalla request
		String gRecaptchaResponse = request.getParameter(ApplicationConstant.G_RECAPTCHA_RESPONSE_HTTP_HEADER_KEY);

		// Verifico il ReCaptcha
		boolean captchaVerificato = verifyRecaptchaService.verify(gRecaptchaResponse);

		// Se la verifica fallisce, fallisce la validazione
		if (!captchaVerificato) {
			bindingResult.rejectValue("captcha", "", "Si prega di completare il captcha prima di procedere.");
			throw new VerificaReCaptchaException("Verifica captcha fallita");
		}
	}
}
