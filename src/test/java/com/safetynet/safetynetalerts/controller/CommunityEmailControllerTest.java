package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.CommunityEmailServiceInterface;

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

public class CommunityEmailControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommunityEmailServiceInterface communityEmailService;

    @InjectMocks
    private CommunityEmailController communityEmailController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(communityEmailController).build();
    }

    @Test
    public void testGetCommunityEmails() throws Exception {
        String city = "TestCity";
        Set<String> emails = new HashSet<>();
        emails.add("test1@example.com");
        emails.add("test2@example.com");

        when(communityEmailService.getEmailsByCity(city)).thenReturn(emails);

        mockMvc.perform(get("/communityEmail").param("city", city)).andExpect(status().isOk())
                .andExpect(content().json("[\"test1@example.com\",\"test2@example.com\"]"));
    }

    @Test
    public void testGetCommunityEmailsEmpty() throws Exception {
        String city = "EmptyCity";
        Set<String> emails = new HashSet<>();

        when(communityEmailService.getEmailsByCity(city)).thenReturn(emails);

        mockMvc.perform(get("/communityEmail").param("city", city)).andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}