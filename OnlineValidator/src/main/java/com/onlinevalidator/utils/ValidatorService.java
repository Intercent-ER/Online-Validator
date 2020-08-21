package com.onlinevalidator.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.onlinevalidator.model.ValidatorEntity;

@Service
public class ValidatorService implements ValidatorServiceInterface {

	private ValidatorEntity entity;
	private List<ValidatorEntity> entityList;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private DatabaseService databaseService;

	@Override
	public List<ValidatorEntity> getAllEntity() throws SQLException {
		
		boolean useDB = true;
		entityList = new ArrayList<ValidatorEntity>();
		
		if(!useDB) {
			if(entityList == null) {
				entityList = new ArrayList<ValidatorEntity>();
				entity = new ValidatorEntity(1, "Ordine", 1);
				entityList.add(entity);
				entity = new ValidatorEntity(2, "Risposta all'ordine", 2);
				entityList.add(entity);
				entity = new ValidatorEntity(3, "DDT", 3);
				entityList.add(entity);
			}
			
		}else {
			
		ResultSet myRs = databaseService.resultSet("SELECT * FROM tipodocumento");

		int size = 0;
		if (myRs != null) {
			try {
				myRs.last();
				size = myRs.getRow();
				myRs.first();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		System.out.println(size);

		
			for (int i = 0; i < size; i++) {
				System.out.println("Va: " + i);
				entity = new ValidatorEntity(myRs.getInt(1), myRs.getString(2), myRs.getInt(3));
				entityList.add(entity);
				myRs.next();
			}
		}
		

		return entityList;

	}

	
	@Override
	public ValidatorEntity getEntity(int id) {
		for (ValidatorEntity entity : entityList) {
			if (entity.getId() == id) {
				return entity;
			}
		}

		return null;

	}
	
	/*
	if (myStmt != null) {
		myStmt.close();
	}

	if (myConn != null) {
		myConn.close();
	}
}
*/

}
