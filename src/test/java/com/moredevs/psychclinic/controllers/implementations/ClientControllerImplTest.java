package com.moredevs.psychclinic.controllers.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moredevs.psychclinic.models.dtos.ClientCreateDTO;
import com.moredevs.psychclinic.models.dtos.ClientDTO;
import com.moredevs.psychclinic.models.dtos.ClientGetDTO;
import com.moredevs.psychclinic.services.ClientService;
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
public class ClientControllerImplTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientControllerImpl clientControllerImpl;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientControllerImpl).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindById_Found() throws Exception {
        ClientGetDTO clientGetDTO = new ClientGetDTO();
        when(clientService.findById(any())).thenReturn(clientGetDTO);

        mockMvc.perform(get("/client/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(clientGetDTO)));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(clientService.findById(any())).thenReturn(null);

        mockMvc.perform(get("/client/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateClient_Success() throws Exception {
        ClientDTO createdClient = new ClientDTO();
        when(clientService.create(any())).thenReturn(createdClient);

        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ClientCreateDTO())))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createdClient)));
    }

    @Test
    void testListAll() throws Exception {
        List<ClientGetDTO> allClients = List.of(new ClientGetDTO(), new ClientGetDTO());
        when(clientService.listAll()).thenReturn(allClients);

        mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-Total-Count"))
                .andExpect(content().json(objectMapper.writeValueAsString(allClients)));
    }

    @Test
    void testDeleteById() throws Exception {
        doNothing().when(clientService).deleteById(any());

        mockMvc.perform(delete("/client/1"))
                .andExpect(status().isNoContent());
    }
}
