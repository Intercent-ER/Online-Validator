package com.onlinevalidator.services;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.onlinevalidator.controller.ResultController;
import com.onlinevalidator.model.TipoFileEnum;
import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.model.Validatore;
import com.onlinevalidator.repository.TipoDocumentoJpaRepositoryInterface;
import com.onlinevalidator.repository.ValidatorJpaRepositoryInterface;
import com.onlinevalidator.utils.FormatCheckerInterface;

@Service
public class ValidatorService implements ValidatorServiceInterface {

	// dichiarazioni provenienti da result
	@Autowired
	FormatCheckerInterface formatChecker;

	private static Logger logger = Logger.getLogger(ResultController.class);

	@Resource
	private TipoDocumentoJpaRepositoryInterface tipoDocumentoRepository;
	@Resource
	private ValidatorJpaRepositoryInterface validatorRepository;

	@Override
	public Tipodocumento getEntity(int id) {

		return tipoDocumentoRepository.findOne(id);

	}

	@Override
	public List<Tipodocumento> getAllEntity() throws SQLException {

		return tipoDocumentoRepository.findAll();
	}

	@Override
	public Tipodocumento getTipodocumentoById(int idTipoDocumento) {
		return tipoDocumentoRepository.findOne(idTipoDocumento);
	}

	public Validatore filtraValidatore(Tipodocumento tipodocumento, TipoFileEnum tipoFileEnum) {

		if (tipodocumento == null) {

			logger.error("Attenzione, invocazione del metodo sbagliata");

			throw new IllegalStateException("Errore 1");
		}
		if (tipoFileEnum == null) {

			throw new IllegalStateException("Errore 2");
		}
		List<Validatore> validatoriSuTipodocumento = tipodocumento.getValidatori();
		for (Validatore validatoreCorrente : validatoriSuTipodocumento) {
			if (tipoFileEnum.equals(validatoreCorrente.getTipoFileEnum())) {

				return validatoreCorrente;
			}
		}
		logger.error("Validatore non trovato");
		return null;
	}

//	public Validatore filtraValidatore(Tipodocumento tipodocumento, TipoFileEnum tipoFileEnum) {
//		return tipodocumento.getValidatori()
//				.stream()
//				.filter(
//						validatore -> validatore.getTipoFileEnum() != null && validatore.getTipoFileEnum().equals(tipoFileEnum)
//						)
//				.findFirst()
//				.orElse(null);
//	}

	public @ResponseBody String uploadFileHandler(MultipartFile file, int id) {

		String fileName = "";
		String fileContent;
		boolean format;
		String finalResult = "";

		format = formatChecker.checkFormat(file.getContentType());

		if (!file.isEmpty() && format) {
			try {
				fileName = file.getOriginalFilename();
				byte[] bytes = file.getBytes();
				fileContent = new String(bytes);
				Tipodocumento documentodavalidare = getTipodocumentoById(id);
				Validatore validatoreXSD = filtraValidatore(documentodavalidare, TipoFileEnum.XSD);
				Validatore validatoreSCHEMATRON = filtraValidatore(documentodavalidare, TipoFileEnum.SCHEMATRON);

				return "Il file " + fileName + " è stato caricato, il contenuto è: " + fileContent + ", e il peso: "
						+ file.getSize() + ", mentre il suo validatore di tipo " + validatoreXSD.getTipoFileEnum()
						+ " è: " + validatoreXSD.getName() + " con id: " + validatoreXSD.getId() + "ed è di tipo"
						+ validatoreXSD.getTipoFileEnum() + "mentre il validatore di tipo "
						+ validatoreSCHEMATRON.getTipoFileEnum() + " è: " + validatoreSCHEMATRON.getName() + " con id: "
						+ validatoreXSD.getId() + "ed è di tipo" + validatoreSCHEMATRON.getTipoFileEnum();

			} catch (Exception e) {
				return "Il file " + fileName + " non è stato caricato: " + e.getMessage();
			}

		} else {

			if (!format) {
				finalResult += "Il formato del file non è corretto, deve essere txt";

				logger.info(file.getContentType());
			}
			if (file.isEmpty()) {
				finalResult += "Il file non può essere vuoto";
			}
		}

		return finalResult;
	}
}
