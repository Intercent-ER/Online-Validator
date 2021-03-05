package com.onlinevalidator.service;

import com.onlinevalidator.dto.RappresentazioneViewer;
import com.onlinevalidator.util.ApplicationLogger;

import java.util.List;

/**
 * @author Manuel Gozzi
 */
public interface RappresentazioneServiceInterface extends ApplicationLogger {

	List<RappresentazioneViewer> filtraRappresentazioniPerTipoDocumento(int idTipoDocumento);

}
