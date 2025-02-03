package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.service.FireService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FireControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FireService fireService;

    @InjectMocks
    private FireController fireController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fireController).build();
    }

    @Test
    public void testGetFireInfo() throws Exception {
        String address = "123 Test St";
        FireDTO fireDTO = new FireDTO();

        when(fireService.getFireInfoByAddress(address)).thenReturn(fireDTO);

        mockMvc.perform(get("/fire").param("address", address).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json("{/* expected JSON response */}"));
    }
}