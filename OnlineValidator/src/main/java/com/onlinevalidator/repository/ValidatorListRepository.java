package com.onlinevalidator.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.onlinevalidator.model.ValidatorEntity;

@Repository(value="validatorListRepository")
public class ValidatorListRepository implements ValidatorRepositoryInterface {

	private ValidatorEntity entity;
	private List<ValidatorEntity> entityList;
	
	@Override
	public List<ValidatorEntity> findAll() {
		if(entityList == null) {
			entityList = new ArrayList<ValidatorEntity>();
			entity = new ValidatorEntity(1, "Ordine", 1);
			entityList.add(entity);
			entity = new ValidatorEntity(2, "Risposta all'ordine", 2);
			entityList.add(entity);
			entity = new ValidatorEntity(3, "DDT", 3);
			entityList.add(entity);
		}
		return entityList;
	}

	@Override
	public ValidatorEntity findOne(int id) {
		for (ValidatorEntity entity : entityList) {
			if (entity.getId() == id) {
				return entity;
			}
		}
		return null;
	}
	
	

}
