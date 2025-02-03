package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.service.ChildAlertServiceInterface;

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

import java.util.Arrays;
import java.util.List;

public class ChildAlertControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ChildAlertServiceInterface childAlertService;

    @InjectMocks
    private ChildAlertController childAlertController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(childAlertController).build();
    }

    @Test
    public void testGetChildAlert() throws Exception {
        String address = "123 Test St";

        ChildAlertDTO child1 = new ChildAlertDTO();
        child1.setFirstName("John");
        child1.setLastName("Doe");
        child1.setAge(10);

        ChildAlertDTO child2 = new ChildAlertDTO();
        child2.setFirstName("Jane");
        child2.setLastName("Doe");
        child2.setAge(8);

        List<ChildAlertDTO> childrenAtAddress = Arrays.asList(child1, child2);

        when(childAlertService.getChildrenAtAddress(address)).thenReturn(childrenAtAddress);

        mockMvc.perform(get("/childAlert").param("address", address)).andExpect(status().isOk()).andExpect(content()
                .json("[{'firstName':'John','lastName':'Doe','age':10},{'firstName':'Jane','lastName':'Doe','age':8}]"));
    }

    @Test
    public void testGetChildAlertNoChildren() throws Exception {
        String address = "456 Empty St";

        List<ChildAlertDTO> childrenAtAddress = Arrays.asList();

        when(childAlertService.getChildrenAtAddress(address)).thenReturn(childrenAtAddress);

        mockMvc.perform(get("/childAlert").param("address", address)).andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}