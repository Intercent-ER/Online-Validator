package com.onlinevalidator.generatedsources.xsd.report;

import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Gozzi
 */
public class FailedAssertTest {

	@Test
	public void getDiagnosticReference() {

		assertTrue(new FailedAssert().getDiagnosticReference().isEmpty());
	}

	@Test
	public void getText() {

		assertNull(new FailedAssert().getText());
	}

	@Test
	public void setText() {

		new FailedAssert().setText("text");
	}

	@Test
	public void getId() {

		assertNull(new FailedAssert().getId());
	}

	@Test
	public void setId() {

		new FailedAssert().setId("id");
	}

	@Test
	public void getLocation() {

		assertNull(new FailedAssert().getLocation());
	}

	@Test
	public void setLocation() {

		new FailedAssert().setLocation("location");
	}

	@Test
	public void getTest() {

		assertNull(new FailedAssert().getTest());
	}

	@Test
	public void setTest() {

		new FailedAssert().setTest("test");
	}

	@Test
	public void getRole() {

		assertNull(new FailedAssert().getRole());
	}

	@Test
	public void setRole() {

		new FailedAssert().setRole("role");
	}

	@Test
	public void getFlag() {

		assertNull(new FailedAssert().getFlag());
	}

	@Test
	public void setFlag() {

		new FailedAssert().setFlag("flag");
	}
}