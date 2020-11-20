package com.onlinevalidator.service;

import org.springframework.stereotype.Service;

@Service
public interface FormatCheckerInterface {
	
	public boolean checkFormat(String string);

}
