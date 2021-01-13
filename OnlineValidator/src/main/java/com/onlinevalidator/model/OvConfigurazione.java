package com.onlinevalidator.model;

import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;

import javax.persistence.*;

/**
 * @author Manuel Gozzi
 */
@Entity
@Table(name = "OV_CONFIGURAZIONE")
@NamedQuery(name = "OvConfigurazione.findAll", query = "SELECT c FROM OvConfigurazione c")
public class OvConfigurazione {

	@Id
	@SequenceGenerator(name = "CONFIGURAZIONE_GENERATOR", allocationSize = 1, sequenceName = "SEQ_OV_CONFIGURAZIONE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONFIGURAZIONE_GENERATOR")
	@Column(name = "ID_CONFIGURAZIONE", unique = true, nullable = false)
	private int idConfigurazione;

	@Column(name = "CD_CHIAVE", length = 150, nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private ChiaveConfigurazioneEnum cdChiaveConfigurazione;

	@Column(name = "CD_VALORE", length = 2048)
	private String cdValoreConfigurazione;

	@Column(name = "DS_DESCRIZIONE", length = 512, nullable = false)
	private String dsDescrizione;

	public OvConfigurazione() {
	}

	public int getIdConfigurazione() {
		return idConfigurazione;
	}

	public void setIdConfigurazione(int idConfigurazione) {
		this.idConfigurazione = idConfigurazione;
	}

	public String getCdChiaveConfigurazione() {
		return cdChiaveConfigurazione;
	}

	public void setCdChiaveConfigurazione(String cdChiaveConfigurazione) {
		this.cdChiaveConfigurazione = cdChiaveConfigurazione;
	}

	public String getCdValoreConfigurazione() {
		return cdValoreConfigurazione;
	}

	public void setCdValoreConfigurazione(String cdValoreConfigurazione) {
		this.cdValoreConfigurazione = cdValoreConfigurazione;
	}

	public String getDsDescrizione() {
		return dsDescrizione;
	}

	public void setDsDescrizione(String dsDescrizione) {
		this.dsDescrizione = dsDescrizione;
	}
}
