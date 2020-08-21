package com.onlinevalidator.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
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

import com.onlinevalidator.model.ValidatorEntity;
import com.onlinevalidator.utils.ValidatorService;
import com.onlinevalidator.utils.DatabaseService;
import com.onlinevalidator.utils.FormatCheckerInterface;

@Controller
public class ResultController {

	String fileName;
	String fileContent;
	public static final String MIME_TEXT_PLAIN = "text/plain";
	String finalResult = "";
	@Autowired
	FormatCheckerInterface formatChecker;
	@Autowired
	ValidatorService entityService;
	@Autowired
	private DatabaseService databaseService;
	boolean format;
	int prova = 1;

	@RequestMapping("/")
	public ModelAndView fileUploader() {

		ModelAndView modelAndView = new ModelAndView("index");
		// List<Entity> userForms = getAllValidatori();
		// modelAndView.addObject("validatori", getAllValidatori());
		return modelAndView;

	}

	@ModelAttribute("validatori")
	public List<ValidatorEntity> getAllValidatori() throws SQLException {
		return entityService.getAllEntity();
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("file") MultipartFile file, @RequestParam(value = "id") int id) {

		format = formatChecker.checkFormat(file.getContentType());

		if (!file.isEmpty() && format) {
			try {
				fileName = file.getOriginalFilename();
				byte[] bytes = file.getBytes();
				fileContent = new String(bytes);
				System.out.println(file.getContentType());
				ValidatorEntity entity = entityService.getEntity(id);
				System.out.println();
				String name;
				if (entity == null) {
					throw new Exception("Valore non valido");
				} else {
					name = entity.getName();
				}
				
				ResultSet resultSet = databaseService.resultSet("SELECT * FROM validatore WHERE id = '" + entity.getIdValidatore() + "'");
				resultSet.first();

				return "Il file " + fileName + " è stato caricato, il contenuto è: " + fileContent + ", e il peso: "
						+ file.getSize() + ", mentre l'entità è: " + name + " con id: " + id + " e il suo validatore è: " + resultSet.getString(2) + " con id: " + resultSet.getInt(1);
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
					System.out.println(file.getContentType());
				}
				if (file.isEmpty()) {
					finalResult += "Il file non può essere vuoto";
				}
			}

			return finalResult;
		}
	}

}
