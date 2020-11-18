package com.onlinevalidator.pojo;

import java.util.ArrayList;
import java.util.Collections;
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
	 * Data una regola di validazione e una descrizione dell'errore di validazione riscontrato, la si aggiunge
	 * alla lista di asserzioni fallite.
	 *
	 * @param codiceRegola      è il codice della regola di riferimento (e.g. [INT-R029])
	 * @param descrizioneErrore è il dettaglio descrittivo dell'errore
	 */
	public void aggiungiDettaglio(ValidationAssert validationAssert) {
		if (erroriDiValidazione == null) {
			erroriDiValidazione = new ArrayList<>();
		}
		erroriDiValidazione.add(
				validationAssert
		);
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
		List<ValidationAssert> assertDiValidazione = new ArrayList<>(erroriDiValidazione.size());
		Collections.copy(erroriDiValidazione, assertDiValidazione);
		return assertDiValidazione;
	}

}
