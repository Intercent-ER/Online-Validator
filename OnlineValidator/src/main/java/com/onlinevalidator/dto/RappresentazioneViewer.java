package com.onlinevalidator.dto;

/**
 * @author Manuel Gozzi
 */
public class RappresentazioneViewer {

	private Integer idRappresentazione;

	private String dsDescrizione;

	public RappresentazioneViewer() {
	}

	public RappresentazioneViewer(Integer idRappresentazione, String dsDescrizione) {
		this.idRappresentazione = idRappresentazione;
		this.dsDescrizione = dsDescrizione;
	}

	public Integer getIdRappresentazione() {
		return idRappresentazione;
	}

	public void setIdRappresentazione(Integer idRappresentazione) {
		this.idRappresentazione = idRappresentazione;
	}

	public String getDsDescrizione() {
		return dsDescrizione;
	}

	public void setDsDescrizione(String dsDescrizione) {
		this.dsDescrizione = dsDescrizione;
	}
}
