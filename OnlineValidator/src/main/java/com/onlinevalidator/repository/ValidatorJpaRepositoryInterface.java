package com.onlinevalidator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinevalidator.model.Tipodocumento;

@Repository
public interface ValidatorJpaRepositoryInterface extends JpaRepository<Tipodocumento, Integer>{
	
	
	
}
