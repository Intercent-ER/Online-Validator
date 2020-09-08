package com.onlinevalidator.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the validatore database table.
 * 
 */
@Entity
@NamedQuery(name="Validatore.findAll", query="SELECT v FROM Validatore v")
public class Validatore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Lob
	private byte[] file;

	private String name;

	//bi-directional many-to-one association to Tipodocumento
	@OneToMany(mappedBy="validatore")
	private List<Tipodocumento> tipodocumentos;

	public Validatore() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getFile() {
		return this.file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Tipodocumento> getTipodocumentos() {
		return this.tipodocumentos;
	}

	public void setTipodocumentos(List<Tipodocumento> tipodocumentos) {
		this.tipodocumentos = tipodocumentos;
	}

	public Tipodocumento addTipodocumento(Tipodocumento tipodocumento) {
		getTipodocumentos().add(tipodocumento);
		tipodocumento.setValidatore(this);

		return tipodocumento;
	}

	public Tipodocumento removeTipodocumento(Tipodocumento tipodocumento) {
		getTipodocumentos().remove(tipodocumento);
		tipodocumento.setValidatore(null);

		return tipodocumento;
	}

}