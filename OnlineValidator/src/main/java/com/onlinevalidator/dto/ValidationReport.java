package com.onlinevalidator.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Classe POJO responsabile di contenere il dettaglio degli errori riscontrati in sede di validazione.
 *
 * @author Manuel Gozzi
 */
public class ValidationReport {

	/*
	 * Lista di ValidationAssert che contiene l'elenco delle regole di validazione violate insieme ai relativi
	 * messaggi d'errore di dettaglio.
	 * Se la validazione va a buon fine, questa lista rimane vuota.
	 */
	private List<ValidationAssert> erroriDiValidazione;

	private String descrizioneErroreXsd;

	private final Date dataDiGenerazione;

	public ValidationReport() {
		this.dataDiGenerazione = new Date();
	}

	/**
	 * Metodo che restituisce <code>true</code> nel caso in cui il risultato di validazione contenga errori,
	 * <code>false</code> altrimenti.
	 *
	 * @return <code>true</code> nel caso in cui l'esito della validazione non sia positivo
	 */
	public boolean contieneErrori() {
		return erroriDiValidazione != null && !erroriDiValidazione.isEmpty();
	}

	/**
	 * Data una regola di validazione e una descrizione dell'errore di validazione
	 * riscontrato, la si aggiunge alla lista di asserzioni fallite.
	 */
	public void aggiungiDettaglio(ValidationAssert validationAssert) {
		if (erroriDiValidazione == null) {
			erroriDiValidazione = new ArrayList<>();
		}
		erroriDiValidazione.add(
				validationAssert
		);
	}

	public void aggiungiDettaglio(String descrizioneErroreXsd) {
		this.descrizioneErroreXsd = descrizioneErroreXsd;
	}

	/**
	 * Metodo getter che restituisce una copia della lista degli assert di validazione.
	 *
	 * @return la lista di errori di validazione relativi a questo report
	 */
	public List<ValidationAssert> getErroriDiValidazione() {
		if (erroriDiValidazione == null || erroriDiValidazione.isEmpty()) {
			return Collections.emptyList();
		}
		return erroriDiValidazione;
	}

	public String getDescrizioneErroreXsd() {
		return descrizioneErroreXsd;
	}

	public void setDescrizioneErroreXsd(String descrizioneErroreXsd) {
		this.descrizioneErroreXsd = descrizioneErroreXsd;
	}

	public Date getDataDiGenerazione() {
		return dataDiGenerazione;
	}
}
