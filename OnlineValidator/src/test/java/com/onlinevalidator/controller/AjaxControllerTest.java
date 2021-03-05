package com.onlinevalidator.controller;

import com.google.gson.Gson;
import com.onlinevalidator.dto.RappresentazioneViewer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Manuel Gozzi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/bean-servlet-test.xml")
@WebAppConfiguration
public class AjaxControllerTest extends AbstractControllerTest {

	@Autowired
	private AjaxController ajaxController;

	@Test
	public void displayRappresentazione() {

		try {

			String string = this.mockMvc.perform(
					MockMvcRequestBuilders.get("/ajax/displayRepresentations")
							.param("idTipoDocumento", "1")
			)
					.andExpect(status().isOk())
					.andReturn()
					.getResponse()
					.getContentAsString();

			new Gson().fromJson(string, RappresentazioneViewer[].class);

			string = this.mockMvc.perform(
					MockMvcRequestBuilders.get("/ajax/displayRepresentations")
							.param("idTipoDocumento", "2")
			)
					.andExpect(status().isOk())
					.andReturn()
					.getResponse()
					.getContentAsString();

			new Gson().fromJson(string, RappresentazioneViewer[].class);
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Override
	protected AjaxController getController() {
		return ajaxController;
	}
}