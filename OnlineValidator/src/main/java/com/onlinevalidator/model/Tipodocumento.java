package com.onlinevalidator.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tipodocumento database table.
 * 
 */
@Entity
@NamedQuery(name="Tipodocumento.findAll", query="SELECT t FROM Tipodocumento t")
public class Tipodocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	//bi-directional many-to-one association to Validatore
	@ManyToOne
	@JoinColumn(name="id_validatore")
	private Validatore validatore;

	public Tipodocumento() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Validatore getValidatore() {
		return this.validatore;
	}

	public void setValidatore(Validatore validatore) {
		this.validatore = validatore;
	}

}