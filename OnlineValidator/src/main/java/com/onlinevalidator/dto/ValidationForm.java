/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinevalidator.dto;

import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author luca.bergonzoni
 */
public class ValidationForm {
    
    @NotNull
    private MultipartFile file;
    private int documento;
    private int formatoDocumento;
    
    private String captcha;

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
    
    
    
    
    
}
