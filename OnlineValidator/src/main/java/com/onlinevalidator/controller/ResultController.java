package com.onlinevalidator.controller;

import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.model.Validatore;
import com.onlinevalidator.service.FormatCheckerInterface;
import com.onlinevalidator.service.impl.ValidatorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ResultController {

	String fileName;
	String fileContent;
	public static final String MIME_TEXT_PLAIN = "text/plain";
	String finalResult = "";
	@Autowired
	FormatCheckerInterface formatChecker;
	@Autowired
	ValidatorService validatorService;
	boolean format;
	int prova = 1;
	private static Logger logger = Logger.getLogger(ResultController.class);

	@RequestMapping("/")
	public ModelAndView fileUploader() {

		ModelAndView modelAndView = new ModelAndView("index");
		// List<Entity> userForms = getAllValidatori();
		// modelAndView.addObject("validatori", getAllValidatori());
		return modelAndView;

	}

	@ModelAttribute("validatori")
	public List<Tipodocumento> getAllValidatori() throws SQLException {
		return validatorService.getAllEntity();
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("file") MultipartFile file, @RequestParam(value = "id") int idTipoDocumento) {

		format = formatChecker.checkFormat(file.getContentType());

		if (!file.isEmpty() && format) {
			try {
				fileName = file.getOriginalFilename();
				byte[] bytes = file.getBytes();
				fileContent = new String(bytes);
				Validatore entity = validatorService.getValidatoreByTipoDocumento(idTipoDocumento);

				return "Il file " + fileName + " è stato caricato, il contenuto è: " + fileContent + ", e il peso: "
						+ file.getSize() + ", mentre il suo validatore è: " + entity.getName() + " con id: " + entity.getId();
			} catch (Exception e) {
				if (file.getSize() > 500000) {
					return "Il file supera la dimensione massima di 0,5 Mb, il tuof file pesa: " + file.getSize();
				} else {
					return "Il file " + fileName + " non è stato caricato: " + e.getMessage();
				}

			}
		} else {
			if (file.getSize() > 500000) {
				finalResult += "Il file supera la dimensione massima di 0,5 Mb, il tuof file pesa: " + file.getSize();
			} else {
				if (!format) {
					finalResult += "Il formato del file non è corretto, deve essere txt";
					//System.out.println(file.getContentType());
					logger.info(file.getContentType());
				}
				if (file.isEmpty()) {
					finalResult += "Il file non può essere vuoto";
				}
			}

			return finalResult;
		}
	}

}
