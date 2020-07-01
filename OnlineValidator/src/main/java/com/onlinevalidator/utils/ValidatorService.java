package com.onlinevalidator.utils;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.onlinevalidator.model.ValidatorEntity;

@Service
public class ValidatorService implements ValidatorServiceInterface{
	
	private ValidatorEntity entity;
	private List<ValidatorEntity> entityList;

	@Override
	public List<ValidatorEntity> getAllEntity() {
		if(entityList == null) {
			entityList = new ArrayList<ValidatorEntity>();
			entity = new ValidatorEntity(1, "Ordine");
			entityList.add(entity);
			entity = new ValidatorEntity(2, "DDT");
			entityList.add(entity);
			entity = new ValidatorEntity(3, "Risposta all'ordine");
			entityList.add(entity);
		}
		
		return entityList;
		
	}
	
	@Override
	public ValidatorEntity getEntity(int id) {
		for (ValidatorEntity entity : entityList) {
			if(entity.getId() == id) {
				return entity;
			}
		}
		
		return null;
		
	}

}
