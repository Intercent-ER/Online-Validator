package com.onlinevalidator.controller;

import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import com.onlinevalidator.service.ConfigurazioneServiceInterface;
import com.onlinevalidator.service.impl.ValidatorService;
import com.onlinevalidator.service.impl.VerifyRecaptchaService;
import com.onlinevalidator.util.CostantiWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorController.class);

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
    public ModelAndView index(HttpSession httpSession) {

        ModelAndView index = new ModelAndView("index");

        try {

            String fileOk = (String) httpSession.getAttribute(CostantiWeb.FILE_UPLOADED);
            if (Boolean.FALSE.toString().equals(fileOk)) {
                index.addObject(CostantiWeb.FILE_UPLOADED, false);
            } else {
                index.addObject(CostantiWeb.FILE_UPLOADED, true);
            }
        } catch (Exception e) {

            logger.warn("Errore durante il recupero del file: {}", e.getMessage());
            index.addObject(CostantiWeb.FILE_UPLOADED, true);
        } finally {

            // Rimuovo l'attributo dopo averlo letto
            httpSession.removeAttribute(CostantiWeb.FILE_UPLOADED);
        }
        
        try {

            String captchaOk = (String) httpSession.getAttribute(CostantiWeb.CAPTCHA_COMPLETED);
            if (Boolean.FALSE.toString().equals(captchaOk)) {
                index.addObject(CostantiWeb.CAPTCHA_COMPLETED, false);
            } else {
                index.addObject(CostantiWeb.CAPTCHA_COMPLETED, true);
            }
        } catch (Exception e) {

            logger.warn("Errore durante il recupero del captcha: {}", e.getMessage());
            index.addObject(CostantiWeb.CAPTCHA_COMPLETED, true);
        } finally {

            // Rimuovo l'attributo dopo averlo letto
            httpSession.removeAttribute(CostantiWeb.CAPTCHA_COMPLETED);
        }

        return index;
    }

    @ModelAttribute("tipoDocumento")
    public List<OvTipoDocumento> getAllTipoDocumento() {
        return tipiDocumento;
    }

}
