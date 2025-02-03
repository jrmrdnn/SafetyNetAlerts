package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
import com.safetynet.safetynetalerts.service.PersonInfoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

public class PersonInfoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersonInfoService personInfoService;

    @InjectMocks
    private PersonInfoController personInfoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(personInfoController).build();
    }

    @Test
    public void testGetPersonInfo() throws Exception {
        String lastName = "Doe";

        PersonInfoDTO person1 = new PersonInfoDTO();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setAddress("123 Main St");
        person1.setEmail("john.doe@example.com");
        person1.setAge(30);
        person1.setMedications(Arrays.asList("med1"));
        person1.setAllergies(Arrays.asList("allergy1"));

        PersonInfoDTO person2 = new PersonInfoDTO();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setAddress("123 Main St");
        person2.setEmail("jane.doe@example.com");
        person2.setAge(28);
        person2.setMedications(Arrays.asList("med2"));
        person2.setAllergies(Arrays.asList("allergy2"));

        List<PersonInfoDTO> personInfoList = Arrays.asList(person1, person2);

        when(personInfoService.getPersonInfoByLastName(lastName)).thenReturn(personInfoList);

        mockMvc.perform(get("/personInfo").param("lastName", lastName)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John")).andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].address").value("123 Main St"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[0].age").value(30)).andExpect(jsonPath("$[0].medications[0]").value("med1"))
                .andExpect(jsonPath("$[0].allergies[0]").value("allergy1"))
                .andExpect(jsonPath("$[1].firstName").value("Jane")).andExpect(jsonPath("$[1].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].address").value("123 Main St"))
                .andExpect(jsonPath("$[1].email").value("jane.doe@example.com"))
                .andExpect(jsonPath("$[1].age").value(28)).andExpect(jsonPath("$[1].medications[0]").value("med2"))
                .andExpect(jsonPath("$[1].allergies[0]").value("allergy2"));
    }
}