/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinevalidator.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author luca.bergonzoni
 */
@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(value = MultipartException.class)
    public ModelAndView handleFileUploadException(MultipartException mpex, HttpServletRequest request) {
        ModelAndView modelAndVew = new ModelAndView("index");
        modelAndVew.addObject("fileSizeError", true);
        return modelAndVew;
    }

}
