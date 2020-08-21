package com.onlinevalidator.utils;

import java.sql.ResultSet;

import org.springframework.stereotype.Service;

@Service
public interface DatabaseServiceInterface {
	
	public ResultSet resultSet(String query);

}
