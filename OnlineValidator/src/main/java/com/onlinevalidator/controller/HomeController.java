package com.onlinevalidator.controller;

import com.onlinevalidator.dto.UploadFileForm;
import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import com.onlinevalidator.service.ConfigurazioneServiceInterface;
import com.onlinevalidator.service.impl.ValidatorService;
import com.onlinevalidator.util.ApplicationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private ConfigurazioneServiceInterface configurazioneService;

    private List<OvTipoDocumento> tipiDocumento;
    private String reCaptchaSiteKey;

    @PostConstruct
    public void init() {
        this.tipiDocumento = validatorService.filtraTuttiITipiDocumento();
        this.reCaptchaSiteKey = configurazioneService.readValue(ChiaveConfigurazioneEnum.G_RECAPTCHA_SITE_KEY);
    }

    @ModelAttribute(ApplicationConstant.INDEX_JSP_RECAPTCHA_SITE_KEY)
    public String gRecaptchaSiteKey() {
        return reCaptchaSiteKey;
    }

    @ModelAttribute(ApplicationConstant.INDEX_JSP_UPLOAD_FILE_FORM)
    public UploadFileForm uploadFileForm() {
        return new UploadFileForm();
    }

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @ModelAttribute(ApplicationConstant.INDEX_JSP_ELENCO_TIPI_DOCUMENTO)
    public List<OvTipoDocumento> getAllTipoDocumento() {
        return tipiDocumento;
    }
}
