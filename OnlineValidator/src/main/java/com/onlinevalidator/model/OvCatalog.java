package com.onlinevalidator.model;


import com.onlinevalidator.model.enumerator.NomeCatalogEnum;

import javax.management.remote.rmi._RMIConnection_Stub;
import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;


/**
 * The persistent class for the catalog database table.
 */
@Entity
@Table(name = "OV_CATALOG")
@NamedQuery(name = "OvCatalog.findAll", query = "SELECT c FROM OvCatalog c")
public class OvCatalog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CATALOG_GENERATOR", allocationSize = 1, sequenceName = "SEQ_OV_CATALOG")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATALOG_GENERATOR")
	@Column(name = "ID_CATALOG", unique = true, nullable = false)
	private int idCatalog;

	@Column(name = "DS_DESCRIZIONE", length = 512)
	private String dsDescrizione;

	@Lob
	@Column(name = "BL_FILE", nullable = false)
	private byte[] blFileCatalog;

	@Enumerated(EnumType.STRING)
	@Column(name = "NM_NOME", nullable = false, length = 50)
	private NomeCatalogEnum nmNome;

	@Column(name = "CD_VERSIONE", nullable = false, length = 50)
	private String cdVersione;

	@Column(name = "CD_URL", nullable = false, length = 512)
	private String cdUrl;

	public OvCatalog() {
	}

	public int getIdCatalog() {
		return this.idCatalog;
	}

	public void setIdCatalog(int id) {
		this.idCatalog = id;
	}

	public String getDsDescrizione() {
		return this.dsDescrizione;
	}

	public void setDsDescrizione(String descrizione) {
		this.dsDescrizione = descrizione;
	}

	public byte[] getBlFileCatalog() {
		return this.blFileCatalog;
	}

	public String getBlFileCatalogAsString() {
		return getBlFileCatalog() != null && getBlFileCatalog().length > 0 ?
				new String(
						getBlFileCatalog(),
						StandardCharsets.UTF_8
				)
				: null;
	}

	public void setBlFileCatalog(byte[] filecatalog) {
		this.blFileCatalog = filecatalog;
	}

	public NomeCatalogEnum getNmNome() {
		return this.nmNome;
	}

	public void setNmNome(NomeCatalogEnum nome) {
		this.nmNome = nome;
	}

	public String getCdVersione() {
		return this.cdVersione;
	}

	public void setCdVersione(String versione) {
		this.cdVersione = versione;
	}

	public String getCdUrl() {
		return cdUrl;
	}

	public void setCdUrl(String cdUrl) {
		this.cdUrl = cdUrl;
	}
}