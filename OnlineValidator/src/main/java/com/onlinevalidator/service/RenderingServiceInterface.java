package com.onlinevalidator.service;

import com.onlinevalidator.dto.Render;
import com.onlinevalidator.dto.ValidationReport;
import com.onlinevalidator.exception.rendering.PdfRenderingException;
import com.onlinevalidator.exception.rendering.RenderingException;
import com.onlinevalidator.exception.rendering.XmlRenderingException;
import com.onlinevalidator.pojo.TipoRenderingEnum;
import com.onlinevalidator.util.ApplicationLogger;

/**
 * @author Manuel Gozzi
 */
public interface RenderingServiceInterface extends ApplicationLogger {

	/**
	 * Esegue il rendering di un report di validazione in formato PDF.
	 *
	 * @param validationReport è il report da verificare
	 * @return il {@link Render} che contiene il risultato
	 * @throws PdfRenderingException nel caso in cui qualcosa vada storto nel processo
	 */
	Render renderToPdf(ValidationReport validationReport) throws PdfRenderingException;

	/**
	 * Esegue il rendering di un report di validazione in formato XML.
	 *
	 * @param validationReport è il report di validazione
	 * @return il {@link Render} che contiene il risultato
	 * @throws XmlRenderingException nel caso in cui qualcosa vada storto nel processo
	 */
	Render renderToXml(ValidationReport validationReport) throws XmlRenderingException;

	/**
	 * Esegue il rendering di un report di validazione, contenente i relativi risultati del processo, a seconda della
	 * tipologia di rendering indicata.
	 *
	 * @param validationReport  è il report di validazione
	 * @param tipoRenderingEnum è il tipo di rendering {@link TipoRenderingEnum}
	 * @return l'oggetto Render che contiene il risultato di validazione
	 * @throws RenderingException nel caso in cui qualcosa vada storto nel processo di rendering
	 *                            {@link XmlRenderingException}, {@link PdfRenderingException}
	 */
	Render render(ValidationReport validationReport, TipoRenderingEnum tipoRenderingEnum) throws RenderingException;

}
