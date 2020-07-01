package com.onlinevalidator.utils;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onlinevalidator.model.ValidatorEntity;

@Service
public interface ValidatorServiceInterface{
	
	public List<ValidatorEntity> getAllEntity();
	
	public ValidatorEntity getEntity(int id);

}
