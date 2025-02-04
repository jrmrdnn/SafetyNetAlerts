package com.safetynet.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddPerson() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");
        person.setCity("City");
        person.setZip("12345");
        person.setPhone("123-456-7890");
        person.setEmail("john.doe@example.com");

        when(personService.addPerson(person)).thenReturn(person);

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person))).andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(person)));
    }

    @Test
    public void testUpdatePerson() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");
        person.setCity("City");
        person.setZip("12345");
        person.setPhone("123-456-7890");
        person.setEmail("john.doe@example.com");

        when(personService.updatePerson(person)).thenReturn(person);

        mockMvc.perform(
                put("/person").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(person)));
    }

    @Test
    public void testDeletePerson() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");
        person.setCity("City");
        person.setZip("12345");
        person.setPhone("123-456-7890");
        person.setEmail("john.doe@example.com");

        mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person))).andExpect(status().isOk())
                .andExpect(content().string("Person deleted successfully: John Doe"));
    }
}