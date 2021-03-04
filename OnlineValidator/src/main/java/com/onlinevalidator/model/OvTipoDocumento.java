package com.onlinevalidator.model;

import com.onlinevalidator.model.enumerator.NomeTipoDocumentoEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the tipodocumento database table.
 */
@Entity
@Table(name = "OV_TIPO_DOCUMENTO")
@NamedQuery(name = "OvTipoDocumento.findAll", query = "SELECT t FROM OvTipoDocumento t")
public class OvTipoDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "OV_TIPO_DOCUMENTO_GENERATOR", allocationSize = 1, sequenceName = "SEQ_OV_TIPO_DOCUMENTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OV_TIPO_DOCUMENTO_GENERATOR")
    @Column(name = "ID_TIPO_DOCUMENTO", unique = true, nullable = false)
    private int idTipoDocumento;

    @Column(name = "NM_NOME", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private NomeTipoDocumentoEnum nmNome;

    @OneToMany(mappedBy = "ovTipoDocumento", fetch = FetchType.EAGER)
    private List<OvRappresentazione> rappresentazione;

    public OvTipoDocumento() {
    }

    public int getIdTipoDocumento() {
        return this.idTipoDocumento;
    }

    public void setIdTipoDocumento(int id) {
        this.idTipoDocumento = id;
    }

    public NomeTipoDocumentoEnum getName() {
        return this.nmNome;
    }

    public void setName(NomeTipoDocumentoEnum nome) {
        this.nmNome = nome;
    }

    public List<OvRappresentazione> getRappresentazione() {
        return rappresentazione;
    }

    public void setRappresentazione(List<OvRappresentazione> rappresentazione) {
        this.rappresentazione = rappresentazione;
    }

}
