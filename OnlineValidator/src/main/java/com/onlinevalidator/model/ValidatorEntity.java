package com.onlinevalidator.model;

public class ValidatorEntity {
	
	private int id;
	private String name;
	private int idValidatore;
	
	public ValidatorEntity(int id, String name, int idValidatore) {
		super();
		this.id = id;
		this.name = name;
		this.idValidatore = idValidatore;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdValidatore() {
		return idValidatore;
	}
	public void setIdValidatore(int idValidatore) {
		this.idValidatore = idValidatore;
	}
	
	
	
	
	
	
}
