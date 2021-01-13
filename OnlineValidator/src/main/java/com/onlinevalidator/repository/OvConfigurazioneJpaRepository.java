package com.onlinevalidator.repository;

import com.onlinevalidator.model.OvConfigurazione;
import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Manuel Gozzi
 */
@Repository
public interface OvConfigurazioneJpaRepository extends JpaRepository<OvConfigurazione, Integer> {

	OvConfigurazione findByCdChiaveConfigurazione(ChiaveConfigurazioneEnum chiaveConfigurazioneEnum);

}
