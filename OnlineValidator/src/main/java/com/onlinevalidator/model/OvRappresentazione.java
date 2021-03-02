package com.onlinevalidator.model;

import com.onlinevalidator.model.enumerator.RappresentazionePaeseEnum;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * @author Manuel Gozzi
 */
public class OvRappresentazione {

    // Tipo documento --- rappresentazione >> 1:N

    // Id (chiave della tabella)
    private int idRappresentazione;

    // Process type della versione del BIS
    private String cdProcessTypeIdentifier;

    // Document tye identifier della versione del BIS
    private String cdDocumentTypeIdentifier;

    // Nome della rappresentazione (visibile in interfaccia)
    private String dsNomeRappresentazione;

    // Versione della rappresentazione
    private String niVersione;

    // Paese di riferimento (IT/EU)
    private RappresentazionePaeseEnum cdRappresentazionePaeseEnum;

    // Tipo documento
    @ManyToOne(fetch = FetchType.EAGER)
    private OvTipoDocumento tipoDocumento;

    public int getIdRappresentazione() {
        return idRappresentazione;
    }

    public void setIdRappresentazione(int idRappresentazione) {
        this.idRappresentazione = idRappresentazione;
    }

    public String getCdProcessTypeIdentifier() {
        return cdProcessTypeIdentifier;
    }

    public void setCdProcessTypeIdentifier(String cdProcessTypeIdentifier) {
        this.cdProcessTypeIdentifier = cdProcessTypeIdentifier;
    }

    public String getCdDocumentTypeIdentifier() {
        return cdDocumentTypeIdentifier;
    }

    public void setCdDocumentTypeIdentifier(String cdDocumentTypeIdentifier) {
        this.cdDocumentTypeIdentifier = cdDocumentTypeIdentifier;
    }

    public String getDsNomeRappresentazione() {
        return dsNomeRappresentazione;
    }

    public void setDsNomeRappresentazione(String dsNomeRappresentazione) {
        this.dsNomeRappresentazione = dsNomeRappresentazione;
    }

    public String getNiVersione() {
        return niVersione;
    }

    public void setNiVersione(String niVersione) {
        this.niVersione = niVersione;
    }

    public RappresentazionePaeseEnum getCdRappresentazionePaeseEnum() {
        return cdRappresentazionePaeseEnum;
    }

    public void setCdRappresentazionePaeseEnum(RappresentazionePaeseEnum cdRappresentazionePaeseEnum) {
        this.cdRappresentazionePaeseEnum = cdRappresentazionePaeseEnum;
    }

    public OvTipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(OvTipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
