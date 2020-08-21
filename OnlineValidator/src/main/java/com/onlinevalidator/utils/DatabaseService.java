package com.onlinevalidator.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinevalidator.model.FileType;

@Service
public class DatabaseService implements DatabaseServiceInterface{
	
	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs = null;
	@Autowired
	private DataSource dataSource;
	
	@Override
	public ResultSet resultSet(String query) {
		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			myRs = myStmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return myRs;
	}

}
