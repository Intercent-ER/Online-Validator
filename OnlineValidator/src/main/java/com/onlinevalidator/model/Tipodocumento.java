package com.onlinevalidator.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the tipodocumento database table.
 *
 */
@Entity
@NamedQuery(name = "Tipodocumento.findAll", query = "SELECT t FROM Tipodocumento t")
public class Tipodocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "name")
	private String name;

	//bi-directional many-to-one association to Validatore
	@OneToMany(mappedBy = "tipodocumento", fetch = FetchType.EAGER)
	private List<Validatore> validatori;

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

	public List<Validatore> getValidatori() {
		return validatori;
	}

	public void setValidatori(List<Validatore> validatori) {
		this.validatori = validatori;
	}
}