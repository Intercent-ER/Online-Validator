package com.onlinevalidator.controller;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * @author Manuel Gozzi
 */
@ContextConfiguration("classpath:/bean-servlet-test.xml")
public abstract class AbstractControllerTest {

	protected MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	@Before
	public void setup() {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		assertNotNull("Controller non inizializzato correttamente", getController());
	}

	protected abstract Object getController();
}
