package com.onlinevalidator.service.impl;

import com.onlinevalidator.service.ConfigurazioneServiceInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Manuel Gozzi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/rootApplicationContext.xml"})
public class ConfigurazioneServiceTest {

	@Autowired
	private ConfigurazioneServiceInterface configurazioneService;

	@Before
	public void before() {

	}

	@Test
	public void readValue() {
	}

	@Test
	public void testReadValue() {
	}
}