/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinevalidator.dto;

import com.onlinevalidator.constraint.annotation.MultipartFileNotNull;
import com.onlinevalidator.constraint.annotation.ValidId;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author luca.bergonzoni
 */
public class UploadFileForm {

    @MultipartFileNotNull(message = "Si prega di selezionare un file prima di procedere.")
    private MultipartFile fileDocumento;

    @ValidId(message = "Si prega di selezionare un tipo di documento dalla lista preposta.")
    private Integer idDocumento;

    @ValidId(message = "Si prega di selezionare un formato dalla lista preposta.")
    private Integer idRappresentazioneDocumento;

    private String captcha;

    public MultipartFile getFileDocumento() {
        return fileDocumento;
    }

    public void setFileDocumento(MultipartFile fileDocumento) {
        this.fileDocumento = fileDocumento;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public int getIdRappresentazioneDocumento() {
        return idRappresentazioneDocumento;
    }

    public void setIdRappresentazioneDocumento(Integer idRappresentazioneDocumento) {
        this.idRappresentazioneDocumento = idRappresentazioneDocumento;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
