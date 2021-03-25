package com.onlinevalidator.controller;

import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import com.onlinevalidator.service.ConfigurazioneServiceInterface;
import com.onlinevalidator.service.impl.ValidatorService;
import com.onlinevalidator.service.impl.VerifyRecaptchaService;
import com.onlinevalidator.util.CostantiWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private ConfigurazioneServiceInterface configurazioneService;
    
    @Autowired
    private VerifyRecaptchaService verifyRecaptchaService;

    private List<OvTipoDocumento> tipiDocumento;

    private String reCaptchaSiteKey;

    @PostConstruct
    public void init() {
        this.tipiDocumento = validatorService.filtraTuttiITipiDocumento();
        this.reCaptchaSiteKey = configurazioneService.readValue(ChiaveConfigurazioneEnum.G_RECAPTCHA_SITE_KEY);
        
    }

    @ModelAttribute("gRecaptchaSiteKey")
    public String gRecaptchaSiteKey() {
        return reCaptchaSiteKey;
    }

    @RequestMapping("/")
    public ModelAndView index(@RequestParam(value="askForCaptcha", required=false) boolean askForCaptcha) {
        ModelAndView index = new ModelAndView("index");
        index.addObject(CostantiWeb.CAPTCHA_COMPLETED, !askForCaptcha);
        return index;
    }

    @ModelAttribute("tipoDocumento")
    public List<OvTipoDocumento> getAllTipoDocumento() {
        return tipiDocumento;
    }

}
