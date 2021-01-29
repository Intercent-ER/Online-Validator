package com.onlinevalidator.repository;

import com.onlinevalidator.model.enumerator.NomeCatalogEnum;
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
public class OvCatalogJpaRepositoryTest {

	@Autowired
	private OvCatalogJpaRepository ovCatalogJpaRepository;

	@Test
	public void getOneBy() {

		assertNotNull(this.ovCatalogJpaRepository);
		this.ovCatalogJpaRepository.findByNmNomeAndCdVersione(NomeCatalogEnum.CategoriaImposte, "2.1");
	}
}