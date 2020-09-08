package com.onlinevalidator.utils;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.model.Validatore;
import com.onlinevalidator.repository.TipoDocumentoJpaRepositoryInterface;
import com.onlinevalidator.repository.ValidatorJpaRepositoryInterface;

@Service
public class ValidatorService implements ValidatorServiceInterface {

	@Resource
	private ValidatorJpaRepositoryInterface repository;
	@Resource
	private TipoDocumentoJpaRepositoryInterface tipoDocumentoRepository;
	
	@Override
	public Tipodocumento getEntity(int id) {
		
		return repository.findOne(id);

	}
	
	@Override
	public List<Tipodocumento> getAllEntity() throws SQLException {

		return repository.findAll();
		
	}

	@Override
	public Validatore getValidatoreByTipoDocumento(int idTipoDocumento) {
		return tipoDocumentoRepository.findOne(idTipoDocumento).getValidatore();
	}

}
