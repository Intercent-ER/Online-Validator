package com.onlinevalidator.model;

import com.onlinevalidator.model.enumerator.TipoFileEnum;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the validatore database table.
 */
@Entity
@Table(name = "OV_VALIDATORE")
@NamedQuery(name = "OvValidatore.findAll", query = "SELECT v FROM OvValidatore v")
public class OvValidatore implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "VALIDATORE_GENERATOR", allocationSize = 1, sequenceName = "SEQ_OV_VALIDATORE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VALIDATORE_GENERATOR")
	@Column(name = "ID_VALIDATORE", unique = true, nullable = false)
	private int idValidatore;

	@Lob
	@Column(name = "BL_FILE", nullable = false)
	private byte[] blFile;

	@Column(name = "NM_NOME", length = 50, nullable = false)
	private String nmNome;

	@Column(name = "CD_TIPO_FILE", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoFileEnum cdTipoFile;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_ID_TIPO_DOCUMENTO", nullable = false)
	private OvTipoDocumento tipoDocumento;

	public OvValidatore() {
	}

	public int getIdValidatore() {
		return this.idValidatore;
	}

	public void setIdValidatore(int id) {
		this.idValidatore = id;
	}

	public byte[] getBlFile() {
		return this.blFile;
	}

	public void setBlFile(byte[] file) {
		this.blFile = file;
	}

	public String getName() {
		return this.nmNome;
	}

	public void setName(String nome) {
		this.nmNome = nome;
	}

	public TipoFileEnum getCdTipoFile() {
		return cdTipoFile;
	}

	public void setCdTipoFile(TipoFileEnum tipoFileEnum) {
		this.cdTipoFile = tipoFileEnum;
	}
}