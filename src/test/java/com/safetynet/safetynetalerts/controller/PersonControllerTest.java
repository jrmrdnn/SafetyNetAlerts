package com.safetynet.safetynetalerts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.service.ReadPersonService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class PersonControllerTest {

  private MockMvc mockMvc;

  @Mock
  private ReadPersonService readPersonService;

  @InjectMocks
  private PersonController personController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
  }

  @Test
  public void testGetChildAlert() throws Exception {
    String address = "123 Test St";

    ChildAlertDTO child1 = new ChildAlertDTO();
    child1.setFirstName("John");
    child1.setLastName("Doe");
    child1.setAge(18);

    ChildAlertDTO child2 = new ChildAlertDTO();
    child2.setFirstName("Jane");
    child2.setLastName("Doe");
    child2.setAge(16);

    List<ChildAlertDTO> childrenAtAddress = Arrays.asList(child1, child2);

    when(readPersonService.allChildrenAtAddress(address)).thenReturn(
      childrenAtAddress
    );

    mockMvc
      .perform(get("/childAlert").param("address", address))
      .andExpect(status().isOk())
      .andExpect(
        content()
          .json(
            "[{'firstName':'John','lastName':'Doe','age':18},{'firstName':'Jane','lastName':'Doe','age':16}]"
          )
      );
  }
}
