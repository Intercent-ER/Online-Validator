package com.onlinevalidator.service.impl;

import com.onlinevalidator.service.LocalServiceUriResolverInterface;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Manuel Gozzi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/bean-servlet-test.xml"})
@Ignore // TODO
public class LocalServiceURIResolverServiceTest {

	@Autowired
	private LocalServiceUriResolverInterface localServiceUriResolver;

	@Test
	@Ignore
	public void splitQuery() {

		// TODO
	}

	@Test
	@Ignore
	public void resolve() {

		// TODO
	}
}