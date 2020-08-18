package com.onlinevalidator.utils;

import org.springframework.stereotype.Service;

import com.onlinevalidator.model.FileType;

@Service
public class FormatChecker implements FormatCheckerInterface{
	
	//public static final String MIME_TEXT_PLAIN = "text/plain";
	
	public boolean checkFormat(String fileFormat) {
		FileType type = new FileType();
		if(type.getFileType().equals(fileFormat)) {
			return true;
		}else {
			return false;
		}
		
	}

}
