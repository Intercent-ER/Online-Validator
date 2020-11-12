package com.onlinevalidator.model;

import javax.persistence.*;
import java.io.Serializable;


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

	@Column(name = "name")
	private String name;

	@Column(name = "tipo_file")
	@Enumerated(EnumType.STRING)
	private TipoFileEnum tipoFileEnum;

	//bi-directional many-to-one association to Tipodocumento
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_tipodocumento")
	private Tipodocumento tipodocumento;

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

	public TipoFileEnum getTipoFileEnum() {
		return tipoFileEnum;
	}

	public void setTipoFileEnum(TipoFileEnum tipoFileEnum) {
		this.tipoFileEnum = tipoFileEnum;
	}
}