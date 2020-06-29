package com.onlinevalidator.utils;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.onlinevalidator.model.Entity;

@Service
public class EntityService implements EntityServiceInterface{
	
	private Entity entity;
	private List<Entity> entityList;

	@Override
	public List<Entity> getAllEntity() {
		if(entityList == null) {
			entityList = new ArrayList<>();
			entity = new Entity(1, "Ordine");
			entityList.add(entity);
			entity = new Entity(2, "DDT");
			entityList.add(entity);
			entity = new Entity(3, "Risposta all'ordine");
			entityList.add(entity);
		}
		
		return entityList;
		
	}
	
	@Override
	public Entity getEntity(int id) {
		for (Entity entity : entityList) {
			if(entity.getId() == id) {
				return entity;
			}
		}
		
		return null;
		
	}

}
