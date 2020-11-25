package com.onlinevalidator.model;


import com.onlinevalidator.model.enumerator.NomeCatalogEnum;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the catalog database table.
 * 
 */
@Entity
@NamedQuery(name="Catalog.findAll", query="SELECT c FROM Catalog c")
public class Catalog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String descrizione;

	@Lob
	private byte[] filecatalog;

	@Enumerated(EnumType.STRING)
	@Column(name = "nome")
	private NomeCatalogEnum nome;

	private String versione;

	private String url;

	public Catalog() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public byte[] getFilecatalog() {
		return this.filecatalog;
	}

	public void setFilecatalog(byte[] filecatalog) {
		this.filecatalog = filecatalog;
	}

	public NomeCatalogEnum getNome() {
		return this.nome;
	}

	public void setNome(NomeCatalogEnum nome) {
		this.nome = nome;
	}

	public String getVersione() {
		return this.versione;
	}

	public void setVersione(String versione) {
		this.versione = versione;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}