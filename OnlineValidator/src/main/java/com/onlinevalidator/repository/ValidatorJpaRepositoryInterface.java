package com.onlinevalidator.repository;

import com.onlinevalidator.model.OvValidatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidatorJpaRepositoryInterface extends JpaRepository<OvValidatore, Integer> {


}
