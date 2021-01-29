package com.onlinevalidator.repository;

import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * @author Manuel Gozzi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/bean-servlet-test.xml")
public class OvConfigurazioneJpaRepositoryTest {

	@Autowired
	private OvConfigurazioneJpaRepository ovConfigurazioneJpaRepository;

	@Test
	public void findByCdChiaveConfigurazione() {

		assertNotNull(this.ovConfigurazioneJpaRepository);
		for (ChiaveConfigurazioneEnum value : ChiaveConfigurazioneEnum.values()) {

			assertNotNull(
					String.format(
							"Chiave di configurazione %s non impostata",
							value.name()
					),
					ovConfigurazioneJpaRepository.findByCdChiaveConfigurazione(value)
			);
		}
	}
}