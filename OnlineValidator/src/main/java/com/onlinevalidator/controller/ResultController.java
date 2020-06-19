package com.onlinevalidator.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.onlinevalidator.utils.FormatCheckerInterface;

@Controller
public class ResultController {
	
	String fileName;
	String fileContent;
	public static final String MIME_TEXT_PLAIN = "text/plain";
	String finalResult = "";
	@Autowired
	FormatCheckerInterface formatChecker;
	boolean format;
	
	@RequestMapping("/")
	public String fileUploader() {
		
		return "index";
		
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("file") MultipartFile file) {
		
		format = formatChecker.checkFormat(file.getContentType());

		if (!file.isEmpty() && format) {
			try {
				fileName = file.getOriginalFilename();
				byte[] bytes = file.getBytes();
				fileContent = new String(bytes);
				System.out.println(file.getContentType());

				/*
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				
				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				*/

				//System.out.println("Dir path" + dir.getAbsolutePath());
				//System.out.println("Server File Location=" + serverFile.getAbsolutePath());
				

				return "Il file " + fileName + " è stato caricato, il contenuto è: " + fileContent;
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


