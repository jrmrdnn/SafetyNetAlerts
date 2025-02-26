package com.safetynet.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SafetynetAlertsApplicationTests {

  @Autowired
  private ApplicationContext applicationContext;

  /**
   * Assert that the beans exist in the application context.
   * @param beanNames
   */
  private void assertBeansExist(List<String> beanNames) {
    for (String beanName : beanNames) {
      assertTrue(applicationContext.containsBean(beanName));
    }
  }

  @Test
  void applicationStartsSuccessfully() {
    SafetynetAlertsApplication.main(new String[] {});
  }

  @Test
  void contextLoads() {
    assertNotNull(applicationContext);

    // Check configuration components
    assertBeansExist(
      Arrays.asList(
        "safetynetAlertsApplication",
        "globalExceptionHandler",
        "loggingAspect",
        "jacksonConfig",
        "objectMapper",
        "jsonWrapper"
      )
    );

    // Check services
    assertBeansExist(
      Arrays.asList(
        "medicalRecordService",
        "calculateAgeService",
        "fireStationService",
        "jacksonService",
        "personService"
      )
    );

    // Check controllers
    assertBeansExist(
      Arrays.asList(
        "medicalRecordController",
        "fireStationController",
        "personController"
      )
    );

    // Check repositories
    assertBeansExist(
      Arrays.asList(
        "medicalRecordRepository",
        "fireStationRepository",
        "personRepository"
      )
    );

    assertTrue(applicationContext.getBeanDefinitionCount() > 0);
  }
}
