package com.onlinevalidator.repository;

import org.springframework.data.repository.CrudRepository;
import com.onlinevalidator.model.ValidatorEntity2;

public interface ValidatorJpaRepositoryInterface extends CrudRepository<ValidatorEntity2, Long>{
	//public interface ValidatorJpaRepositoryInterface extends CrudRepository<ValidatorEntity2, Integer>
}
