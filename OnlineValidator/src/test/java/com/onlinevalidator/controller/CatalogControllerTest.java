package com.onlinevalidator.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

@Ignore
public class CatalogControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private CatalogController catalogController;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void getCatalog() {

		assertNotNull("Contesto non inizializzato propriamente", this.catalogController);

		try {

			mockMvc.perform(
					get("/" + CatalogController.CATALOG_BASE_URL, "nomeCatalog=" + UnitOfMeasureCode.name(), "versione=1.0")
			)
					.andExpect(
							status().isOk()
					);
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}
}