package com.onlinevalidator.dto;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Classe POJO responsabile di contenere il dettaglio degli errori riscontrati
 * in sede di validazione.
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

    private boolean isValido;

    private String documentoValidato;

    private String versioneValidatore;

    private String tipoDocumentoValidato;

    private String formatoDocumentoValidato;

    public ValidationReport() {
        this.dataDiGenerazione = new Date();
        this.isValido = true;
    }

    public ValidationReport(byte[] documentoValidato) {
        this.dataDiGenerazione = new Date();
        this.isValido = true;
        this.setDocumentoValidato(documentoValidato);
    }

    /**
     * Metodo che restituisce <code>true</code> nel caso in cui il risultato di
     * validazione contenga errori, <code>false</code> altrimenti.
     *
     * @return <code>true</code> nel caso in cui l'esito della validazione non
     * sia positivo
     */
    public boolean contieneErrori() {
        return erroriDiValidazione != null && !erroriDiValidazione.isEmpty();
    }

    /**
     * Data una regola di validazione e una descrizione dell'errore di
     * validazione riscontrato, la si aggiunge alla lista di asserzioni fallite.
     */
    public void aggiungiDettaglio(ValidationAssert validationAssert) {
        if (erroriDiValidazione == null) {
            erroriDiValidazione = new ArrayList<>();
        }
        erroriDiValidazione.add(
                validationAssert
        );
        if (validationAssert.isFatal()) {
            this.isValido = false;
        }
    }

    public void aggiungiDettaglio(String descrizioneErroreXsd) {
        this.descrizioneErroreXsd = descrizioneErroreXsd;
    }

    /**
     * Metodo getter che restituisce una copia della lista degli assert di
     * validazione.
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
        this.isValido = false;
    }

    public Date getDataDiGenerazione() {
        return dataDiGenerazione;
    }

    public boolean isValido() {
        return isValido;
    }

    public void setDocumentoValidato(byte[] documentoValidato) {

        this.documentoValidato = new String(documentoValidato, StandardCharsets.UTF_8).trim();
    }

    public String getDocumentoValidato() {
        return this.documentoValidato;
    }

    public String getVersioneValidatore() {
        return versioneValidatore;
    }

    public void setVersioneValidatore(String versioneValidatore) {
        this.versioneValidatore = versioneValidatore;
    }

    public String getTipoDocumentoValidato() {
        return tipoDocumentoValidato;
    }

    public void setTipoDocumentoValidato(String tipoDocumentoValidato) {
        this.tipoDocumentoValidato = tipoDocumentoValidato;
    }

    public String getFormatoDocumentoValidato() {
        return formatoDocumentoValidato;
    }

    public void setFormatoDocumentoValidato(String formatoDocumentoValidato) {
        this.formatoDocumentoValidato = formatoDocumentoValidato;
    }
}
