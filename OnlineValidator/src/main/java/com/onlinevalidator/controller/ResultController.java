package com.onlinevalidator.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.model.Validatore;
import com.onlinevalidator.utils.FormatCheckerInterface;
import com.onlinevalidator.utils.ValidatorService;

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

	@ModelAttribute("tipoDocumento")
	public List<Tipodocumento> getAllTipoDocumento() throws SQLException {
		return validatorService.getAllEntity();
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("file") MultipartFile file, @RequestParam(value = "id") Tipodocumento tipoDocumento) {

		format = formatChecker.checkFormat(file.getContentType());

		if (!file.isEmpty() && format) {
			try {
				fileName = file.getOriginalFilename();
				byte[] bytes = file.getBytes();
				fileContent = new String(bytes);
				Validatore validore1 = validatorService.getValidatori(tipoDocumento).get(0);

				return "Il file " + fileName + " è stato caricato, il contenuto è: " + fileContent + ", e il peso: "
						+ file.getSize() + ", mentre il suo validatore è: " + validore1.getName() + " con id: " + validore1.getId();
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
