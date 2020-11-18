package com.onlinevalidator.utils;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.onlinevalidator.model.TipoFileEnum;
import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.model.Validatore;
import com.onlinevalidator.repository.TipoDocumentoJpaRepositoryInterface;
import com.onlinevalidator.repository.ValidatorJpaRepositoryInterface;

@Service
public class ValidatorService implements ValidatorServiceInterface {
	
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
	public Validatore getXSDValidator(Tipodocumento docdavalidare) {
		
		Validatore val1= docdavalidare.getValidatori().get(0);
		TipoFileEnum TIPO= val1.getTipoFileEnum();
		if(TIPO == TipoFileEnum.XSD) {
			return val1;}
		else {
		return docdavalidare.getValidatori().get(1);
		}
	}
	public Validatore getSCHEMATRONValidator(Tipodocumento docdavalidare) {
		Validatore val1= docdavalidare.getValidatori().get(0);
		TipoFileEnum TIPO= val1.getTipoFileEnum();
		if(TIPO == TipoFileEnum.SCHEMATRON) {
			return val1;}
		else {
			return docdavalidare.getValidatori().get(1);
	    }
	}
	
	
}
	
	
