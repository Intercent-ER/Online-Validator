package com.onlinevalidator.utils;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onlinevalidator.model.Entity;

@Service
public interface EntityServiceInterface{
	
	public List<Entity> getAllEntity();
	
	public Entity getEntity(int id);

}
