package com.onlinevalidator.controller;

import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.repository.OvTipoDocumentoJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Manuel Gozzi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/bean-servlet-test.xml")
@WebAppConfiguration
public class HomeControllerTest extends AbstractControllerTest {

	@Autowired
	private OvTipoDocumentoJpaRepository tipoDocumentoRepository;

	@Autowired
	private HomeController homeController;

	@Override
	protected Object getController() {
		return homeController;
	}

	@Test
	public void index() {

		try {

			this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
					.andExpect(status().isOk())
					.andExpect(view().name("index"))
					.andExpect(model().hasNoErrors());
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void getAllTipoDocumento() {

		List<OvTipoDocumento> ovTipoDocumentos = homeController.getAllTipoDocumento();
		List<OvTipoDocumento> expected = tipoDocumentoRepository.findAll();

		assertNotNull(ovTipoDocumentos);
		assertFalse(ovTipoDocumentos.isEmpty());
		assertEquals(expected.size(), ovTipoDocumentos.size());

		for (OvTipoDocumento ovTipoDocumento : ovTipoDocumentos) {
			try {

				expected.stream()
						.filter(f -> f.getIdTipoDocumento() == ovTipoDocumento.getIdTipoDocumento())
						.findFirst()
						.orElseThrow(IllegalStateException::new);
			} catch (Exception e) {

				fail(e.getMessage());
			}
		}
	}
}