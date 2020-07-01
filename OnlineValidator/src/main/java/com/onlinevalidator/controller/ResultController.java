package com.onlinevalidator.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
	boolean format;
	
	@RequestMapping("/")
	public ModelAndView fileUploader() {
		
		ModelAndView modelAndView = new ModelAndView("index") ;
        //List<Entity> userForms = getAllValidatori();
        //modelAndView.addObject("validatori", getAllValidatori());  
        return modelAndView;
		
	}
	
	@ModelAttribute("validatori")
	public List<ValidatorEntity> getAllValidatori(){
		return entityService.getAllEntity();
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("file") MultipartFile file, @RequestParam(value="id") int id) {
		
		format = formatChecker.checkFormat(file.getContentType());

		if (!file.isEmpty() && format) {
			try {
				fileName = file.getOriginalFilename();
				byte[] bytes = file.getBytes();
				fileContent = new String(bytes);
				System.out.println(file.getContentType());
				ValidatorEntity entity = entityService.getEntity(id);
				String name;
				//Prova
				if(entity == null) {
					throw new Exception("Valore non valido");
				}else {
					 name = entity.getName();
				}

				return "Il file " + fileName + " è stato caricato, il contenuto è: " + fileContent + ", mentre l'entità è: " + name + " con id: " + id;
			} catch (Exception e) {
				return "Il file " + fileName + " non è stato caricato: " + e.getMessage();
			}
		} else {

			if(!format) {
				finalResult += "Il formato del file non è corretto, deve essere txt";
				System.out.println(file.getContentType());
			}
			if(file.isEmpty()){
				finalResult += "Il file non può essere vuoto";
			}
			return finalResult;
		}
	}
	
	

}


