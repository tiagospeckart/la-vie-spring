package com.moredevs.psychclinic.controllers.implementations;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.moredevs.psychclinic.models.dtos.AdminDTO;
import com.moredevs.psychclinic.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class AdminControllerImplTest {
    @Mock
    AdminService adminService;

    @InjectMocks
    AdminControllerImpl adminControllerImpl;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminControllerImpl).build();
    }

    @Test
    void testFindById() throws Exception {
        AdminDTO adminDTO = new AdminDTO();  // Initialize your AdminDTO
        when(adminService.findById(any())).thenReturn(adminDTO);

        mockMvc.perform(get("/admin/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(adminDTO)));

        verify(adminService, times(1)).findById(any());
    }

    @Test
    void testUpdateById() throws Exception {
        AdminDTO existingAdmin = new AdminDTO();  // Initialize your AdminDTO
        when(adminService.findById(any())).thenReturn(existingAdmin);

        AdminDTO updatedAdmin = new AdminDTO();  // Initialize your updated AdminDTO
        when(adminService.update(any())).thenReturn(updatedAdmin);

        mockMvc.perform(put("/admin/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedAdmin)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(updatedAdmin)));

        verify(adminService, times(1)).update(any());
    }

    @Test
    void testDeleteById() throws Exception {
        doNothing().when(adminService).deleteById(any());

        mockMvc.perform(delete("/admin/1"))
                .andExpect(status().isNoContent());

        verify(adminService, times(1)).deleteById(any());
    }

    @Test
    void testListAll() throws Exception {
        List<AdminDTO> allAdmins = Arrays.asList(new AdminDTO(), new AdminDTO());  // Initialize your list
        when(adminService.listAll()).thenReturn(allAdmins);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(allAdmins)));

        verify(adminService, times(1)).listAll();
    }
}
