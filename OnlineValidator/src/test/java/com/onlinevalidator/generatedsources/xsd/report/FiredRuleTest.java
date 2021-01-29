package com.onlinevalidator.generatedsources.xsd.report;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * @author Manuel Gozzi
 */
public class FiredRuleTest {

	@Test
	public void getId() {

		assertNull(new FiredRule().getId());
	}

	@Test
	public void setId() {

		new FiredRule().setId("id");
	}

	@Test
	public void getContext() {

		assertNull(new FiredRule().getContext());
	}

	@Test
	public void setContext() {

		new FiredRule().setContext("context");
	}

	@Test
	public void getRole() {

		assertNull(new FiredRule().getRole());
	}

	@Test
	public void setRole() {

		new FiredRule().setRole("role");
	}

	@Test
	public void getFlag() {

		assertNull(new FiredRule().getFlag());
	}

	@Test
	public void setFlag() {

		new FiredRule().setFlag("flag");
	}
}