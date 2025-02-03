package com.safetynet.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SafetynetAlertsApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertNotNull(applicationContext);
		// assertTrue(applicationContext.containsBean("SafetynetalertsApplication"));
		assertTrue(applicationContext.getBeanDefinitionCount() > 0);
	}

	@Test
	void applicationStartsSuccessfully() {
		SafetynetAlertsApplication.main(new String[] {});
	}
}