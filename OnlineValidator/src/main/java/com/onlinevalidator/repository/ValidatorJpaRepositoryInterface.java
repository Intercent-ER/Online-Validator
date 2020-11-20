package com.onlinevalidator.repository;

import com.onlinevalidator.model.Validatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidatorJpaRepositoryInterface extends JpaRepository<Validatore, Integer> {


}
