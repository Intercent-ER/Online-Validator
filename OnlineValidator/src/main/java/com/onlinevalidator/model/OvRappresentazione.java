package com.onlinevalidator.model;

import com.onlinevalidator.model.enumerator.RappresentazionePaeseEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Manuel Gozzi
 */
@Entity
@Table(name = "OV_RAPPRESENTAZIONE")
@NamedQuery(name = "OvRappresentazione.findAll", query = "SELECT c FROM OvRappresentazione c")
public class OvRappresentazione implements Serializable {

    private static final long serialVersionUID = 1L;

    // Tipo documento --- rappresentazione >> 1:N
    // Id (chiave della tabella)@Id
    @SequenceGenerator(name = "OV_RAPPRESENTAZIONE_GENERATOR", allocationSize = 1, sequenceName = "SEQ_OV_RAPPRESNETAZIONE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OV_RAPPRESENTAZIONE_GENERATOR")
    @Column(name = "ID_RAPPRESENTAZIONE", unique = true, nullable = false)
    private int idRappresentazione;

    // Process type della versione del BIS
    @Column(name = "CD_PROCESS_TYPE_IDENTIFIER", length = 64, nullable = false)
    private String cdProcessTypeIdentifier;

    // Document tye identifier della versione del BIS
    @Column(name = "CD_DOCUMENT_TYPE_IDENTIFIER", length = 512, nullable = false)
    private String cdDocumentTypeIdentifier;

    // Nome della rappresentazione (visibile in interfaccia)
    @Column(name = "DS_NOME_RAPPRESENTAZIONE", length = 64, nullable = false)
    private String dsNomeRappresentazione;

    // Versione della rappresentazione
    @Column(name = "NI_VERSIONE", length = 10, nullable = false)
    private String niVersione;

    // Paese di riferimento (IT/EU)
    @Column(name = "CD_RAPPRESENTAZIONE_PAESE_ENUM", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private RappresentazionePaeseEnum cdRappresentazionePaeseEnum;

    // Validatore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ovRappresentazione")
    private List<OvValidatore> ovValidatore;

    // Tipo di documento
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_TIPO_DOCUMENTO", nullable = false)
    private OvTipoDocumento ovTipoDocumento;

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

    public List<OvValidatore> getOvValidatore() {
        return ovValidatore;
    }

    public void setOvValidatore(List<OvValidatore> ovValidatore) {
        this.ovValidatore = ovValidatore;
    }

    public OvTipoDocumento getOvTipoDocumento() {
        return ovTipoDocumento;
    }

    public void setOvTipoDocumento(OvTipoDocumento ovTipoDocumento) {
        this.ovTipoDocumento = ovTipoDocumento;
    }

}
