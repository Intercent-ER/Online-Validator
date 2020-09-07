package com.onlinevalidator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name="validtor_tbl")
public class ValidatorEntity2 {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="name")
    private String name;
	@Column(name="idVal")
    private int idValidatore;
 
    
    protected ValidatorEntity2() {
    }
    
    protected ValidatorEntity2(String name, int idValidatore) {
        this.name = name;
        this.idValidatore = idValidatore;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
