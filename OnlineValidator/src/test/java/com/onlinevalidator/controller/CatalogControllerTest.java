package com.onlinevalidator.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.onlinevalidator.model.enumerator.NomeCatalogEnum.UnitOfMeasureCode;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Manuel Gozzi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/bean-servlet-test.xml")
@WebAppConfiguration
public class CatalogControllerTest extends AbstractControllerTest {

	@Autowired
	private CatalogController catalogController;

	@Override
	protected Object getController() {
		return catalogController;
	}

	@Test
	public void getCatalog() {

		assertNotNull("Contesto non inizializzato propriamente", this.catalogController);

		try {

			mockMvc.perform(
					get("/catalog")
							.param("nomeCatalog", UnitOfMeasureCode.name())
							.param("versione", "2.1"))
					.andExpect(
							status().isOk()
					);
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}
}