package com.onlinevalidator.repository;

import com.onlinevalidator.model.Tipodocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocumentoJpaRepositoryInterface extends JpaRepository<Tipodocumento, Integer>{

}
