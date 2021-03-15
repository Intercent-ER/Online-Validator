package com.onlinevalidator.dto;

import java.time.LocalDateTime;

/**
 * @author Manuel Gozzi
 */
public class SessionFilterStorage {

	private Integer filtroRappresentazione;

	private Integer filtroTipoDocumento;

	private final LocalDateTime createdOn = LocalDateTime.now();

	public SessionFilterStorage() {
	}

	public SessionFilterStorage(Integer filtroRappresentazione, Integer filtroTipoDocumento) {
		this.filtroRappresentazione = filtroRappresentazione;
		this.filtroTipoDocumento = filtroTipoDocumento;
	}

	public Integer getFiltroRappresentazione() {
		return filtroRappresentazione;
	}

	public void setFiltroRappresentazione(Integer filtroRappresentazione) {
		this.filtroRappresentazione = filtroRappresentazione;
	}

	public Integer getFiltroTipoDocumento() {
		return filtroTipoDocumento;
	}

	public void setFiltroTipoDocumento(Integer filtroTipoDocumento) {
		this.filtroTipoDocumento = filtroTipoDocumento;
	}

	public boolean isExpired() {
		return LocalDateTime.now().minusMinutes(5L).isAfter(createdOn);
	}

}
