package com.moredevs.psychclinic.controllers.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moredevs.psychclinic.exceptions.NotFoundException;
import com.moredevs.psychclinic.models.dtos.AdminDTO;
import com.moredevs.psychclinic.models.dtos.AdminGetDTO;
import com.moredevs.psychclinic.services.AdminService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerImplTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminControllerImpl adminControllerImpl;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminControllerImpl)
                .setViewResolvers(viewResolver)
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testFindById_Found() throws Exception {
        AdminGetDTO adminGetDTO = new AdminGetDTO();
        when(adminService.findById(any())).thenReturn(adminGetDTO);

        mockMvc.perform(get("/admin/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(adminGetDTO)));
    }

    @Test
    public void testFindById_NotFound() throws Exception {
        when(adminService.findById(any())).thenThrow(new NotFoundException());

        mockMvc.perform(get("/admin/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateById_Success() throws Exception {
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
    public void testUpdateById_NotFound() throws Exception {
        when(adminService.findById(any())).thenReturn(null);

        AdminDTO updatedAdmin = new AdminDTO();

        mockMvc.perform(put("/admin/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAdmin)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateById_BadRequest() throws Exception {
        mockMvc.perform(put("/admin/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteById() throws Exception {
        doNothing().when(adminService).deleteById(any());

        mockMvc.perform(delete("/admin/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testListAll() throws Exception {
        List<AdminGetDTO> allAdmins = List.of(new AdminGetDTO(), new AdminGetDTO());
        when(adminService.listAll()).thenReturn(allAdmins);

        MvcResult result = mockMvc.perform(get("/admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-Total-Count"))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<AdminGetDTO> returnedAdmins = objectMapper.readValue(responseBody, List.class);

        // You can add assertions for the returnedAdmins as needed

        // Verify that adminService.listAll() is called once
        verify(adminService, times(1)).listAll();
    }
}
