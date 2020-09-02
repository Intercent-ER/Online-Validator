package com.onlinevalidator.repository;

import java.util.List;

import com.onlinevalidator.model.ValidatorEntity;

public interface ValidatorRepositoryInterface {
	
	public List<ValidatorEntity> findAll();
	
	public ValidatorEntity findOne(int id);

}
