package com.onlinevalidator.generatedsources.xsd.report;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * @author Manuel Gozzi
 */
public class ActivePatternTest {

	@Test
	public void getId() {

		assertNull(new ActivePattern().getId());
	}

	@Test
	public void setId() {

		new ActivePattern().setId("prova");
	}

	@Test
	public void getName() {

		assertNull(new ActivePattern().getName());
	}

	@Test
	public void setName() {

		new ActivePattern().setName("nome");
	}

	@Test
	public void getRole() {

		assertNull(new ActivePattern().getRole());
	}

	@Test
	public void setRole() {

		new ActivePattern().setRole("role");
	}

	@Test
	public void getDocument() {

		assertNull(new ActivePattern().getDocument());
	}

	@Test
	public void setDocument() {

		new ActivePattern().setDocument("document");
	}
}