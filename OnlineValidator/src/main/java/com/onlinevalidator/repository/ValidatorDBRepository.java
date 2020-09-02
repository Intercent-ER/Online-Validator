package com.onlinevalidator.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlinevalidator.model.ValidatorEntity;
import com.onlinevalidator.utils.DatabaseService;
import com.onlinevalidator.utils.DatabaseServiceInterface;

@Repository(value="validatorDBRepository")
public class ValidatorDBRepository implements ValidatorRepositoryInterface{

	@Autowired
	private DatabaseServiceInterface databaseService;
	
	@Override
	public List<ValidatorEntity> findAll() {
		
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
		
		return null;
	}

	@Override
	public ValidatorEntity findOne(int id) {
		ResultSet myRs = databaseService.resultSet("SELECT * FROM tipodocumento WHERE id = '" + id + "'");
		return null;
	}
	
	

}
