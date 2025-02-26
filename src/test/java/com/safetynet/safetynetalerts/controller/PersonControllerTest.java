package com.safetynet.safetynetalerts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.service.ReadPersonService;
import com.safetynet.safetynetalerts.service.WritePersonService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class PersonControllerTest {

  private MockMvc mockMvc;

  @Mock
  private ReadPersonService readPersonService;

  @Mock
  private WritePersonService writePersonService;

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

  @Test
  public void testGetPersonInfo() throws Exception {
    String lastName = "Doe";

    PersonInfoDTO person1 = new PersonInfoDTO();
    person1.setFirstName("John");
    person1.setLastName(lastName);
    person1.setAge(30);
    person1.setMedications(Arrays.asList("med1"));
    person1.setAllergies(Arrays.asList("allergy1"));

    PersonInfoDTO person2 = new PersonInfoDTO();
    person2.setFirstName("Jane");
    person2.setLastName(lastName);
    person2.setAge(28);
    person2.setMedications(Arrays.asList("med2"));
    person2.setAllergies(Arrays.asList("allergy2"));

    List<PersonInfoDTO> personInfoList = Arrays.asList(person1, person2);

    when(readPersonService.getPersonInfoByLastName(lastName)).thenReturn(
      personInfoList
    );

    mockMvc
      .perform(get("/personInfo").param("lastName", lastName))
      .andExpect(status().isOk())
      .andExpect(
        content()
          .json(
            "[{'firstName':'John','lastName':'Doe','age':30,'medications':['med1'],'allergies':['allergy1']},{'firstName':'Jane','lastName':'Doe','age':28,'medications':['med2'],'allergies':['allergy2']}]"
          )
      );
  }

  @Test
  public void testGetCommunityEmails() throws Exception {
    String city = "TestCity";
    Set<String> emails = new HashSet<>();
    emails.add("test1@example.com");
    emails.add("test2@example.com");

    when(readPersonService.getEmailsByCity(city)).thenReturn(emails);

    mockMvc
      .perform(get("/communityEmail").param("city", city))
      .andExpect(status().isOk())
      .andExpect(
        content().json("[\"test1@example.com\",\"test2@example.com\"]")
      );
  }

  @Test
  public void testAddPerson() throws Exception {
    mockMvc
      .perform(
        post("/person")
          .contentType(MediaType.APPLICATION_JSON)
          .content("{\"firstName\":\"John\",\"lastName\":\"Doe\"}")
      )
      .andExpect(status().isOk())
      .andExpect(
        content().json("{\"firstName\":\"John\",\"lastName\":\"Doe\"}")
      );
  }

  @Test
  public void testUpdatePerson() throws Exception {
    mockMvc
      .perform(
        put("/person")
          .contentType(MediaType.APPLICATION_JSON)
          .content("{\"firstName\":\"John\",\"lastName\":\"Doe\"}")
      )
      .andExpect(status().isOk())
      .andExpect(
        content().json("{\"firstName\":\"John\",\"lastName\":\"Doe\"}")
      );
  }

  @Test
  public void testDeletePerson() throws Exception {
    mockMvc
      .perform(
        delete("/person")
          .contentType(MediaType.APPLICATION_JSON)
          .content("{\"firstName\":\"John\",\"lastName\":\"Doe\"}")
      )
      .andExpect(status().isOk())
      .andExpect(content().string("Person deleted: John Doe"));
  }
}
