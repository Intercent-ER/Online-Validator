/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinevalidator.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author luca.bergonzoni
 */
public class ValidationForm {
    
    @NotNull
    @Size(message="Per proseguire, caricare il file")
    private MultipartFile file;
    @NotNull
    private int documento;
    @NotNull
    private int formatoDocumento;
    @NotNull
    @Size(message="Per proseguire, è necessario completare il captcha")
    private String captcha;
    
    private String selectDocumento;
    private String selectFormatoDocumento;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public int getFormatoDocumento() {
        return formatoDocumento;
    }

    public void setFormatoDocumento(int formatoDocumento) {
        this.formatoDocumento = formatoDocumento;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getSelectDocumento() {
        return selectDocumento;
    }

    public void setSelectDocumento(String selectDocumento) {
        this.selectDocumento = selectDocumento;
    }

    public String getSelectFormatoDocumento() {
        return selectFormatoDocumento;
    }

    public void setSelectFormatoDocumento(String selectFormatoDocumento) {
        this.selectFormatoDocumento = selectFormatoDocumento;
    }
    
    
    
    
    
}
