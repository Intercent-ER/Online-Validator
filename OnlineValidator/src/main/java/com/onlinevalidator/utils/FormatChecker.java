package com.onlinevalidator.utils;

import org.springframework.stereotype.Service;

@Service
public class FormatChecker implements FormatCheckerInterface{
	
	public static final String MIME_TEXT_PLAIN = "text/plain";
	
	public boolean checkFormat(String fileFormat) {
		if(fileFormat.equals(MIME_TEXT_PLAIN)) {
			return true;
		}else {
			return false;
		}
		
	}

}
