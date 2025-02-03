package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.PhoneAlertService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

public class PhoneAlertControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PhoneAlertService phoneAlertService;

    @InjectMocks
    private PhoneAlertController phoneAlertController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(phoneAlertController).build();
    }

    @Test
    public void testGetPhoneAlert() throws Exception {
        String firestation = "1";
        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("123-456-7890");
        phoneNumbers.add("098-765-4321");

        when(phoneAlertService.getPhoneNumbersByFirestation(firestation)).thenReturn(phoneNumbers);

        mockMvc.perform(get("/phoneAlert").param("firestation", firestation)).andExpect(status().isOk())
                .andExpect(content().json("[\"123-456-7890\",\"098-765-4321\"]"));
    }
}