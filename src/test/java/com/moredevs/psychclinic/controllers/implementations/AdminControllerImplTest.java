package com.moredevs.psychclinic.controllers.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moredevs.psychclinic.exceptions.NotFoundException;
import com.moredevs.psychclinic.models.dtos.AdminDTO;
import com.moredevs.psychclinic.models.dtos.AdminGetDTO;
import com.moredevs.psychclinic.services.AdminService;
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

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AdminControllerImplTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminControllerImpl adminControllerImpl;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminControllerImpl).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindById_Found() throws Exception {
        AdminGetDTO adminGetDTO = new AdminGetDTO();
        when(adminService.findById(any())).thenReturn(adminGetDTO);

        mockMvc.perform(get("/admin/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(adminGetDTO)));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(adminService.findById(any())).thenThrow(new NotFoundException());

        mockMvc.perform(get("/admin/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testUpdateById_Success() throws Exception {
        AdminGetDTO existingAdmin = new AdminGetDTO();
        when(adminService.findById(any())).thenReturn(existingAdmin);

        AdminDTO updatedAdmin = new AdminDTO();
        when(adminService.update(any())).thenReturn(updatedAdmin);

        mockMvc.perform(put("/admin/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAdmin)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedAdmin)));
    }

    @Test
    void testUpdateById_NotFound() throws Exception {
        when(adminService.findById(any())).thenReturn(null);

        AdminDTO updatedAdmin = new AdminDTO();

        mockMvc.perform(put("/admin/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAdmin)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateById_BadRequest() throws Exception {
        mockMvc.perform(put("/admin/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteById() throws Exception {
        doNothing().when(adminService).deleteById(any());

        mockMvc.perform(delete("/admin/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testListAll() throws Exception {
        List<AdminGetDTO> allAdmins = List.of(new AdminGetDTO(), new AdminGetDTO());
        when(adminService.listAll()).thenReturn(allAdmins);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-Total-Count"))
                .andExpect(content().json(objectMapper.writeValueAsString(allAdmins)));
    }
}
