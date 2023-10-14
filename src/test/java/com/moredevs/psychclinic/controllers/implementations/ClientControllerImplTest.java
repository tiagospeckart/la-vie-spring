package com.moredevs.psychclinic.controllers.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moredevs.psychclinic.models.dtos.ClientCreateDTO;
import com.moredevs.psychclinic.models.dtos.ClientDTO;
import com.moredevs.psychclinic.models.dtos.ClientGetDTO;
import com.moredevs.psychclinic.services.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientControllerImplTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientControllerImpl clientControllerImpl;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before // Use @Before instead of @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientControllerImpl).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testFindById_Found() throws Exception {
        ClientGetDTO clientGetDTO = new ClientGetDTO();
        when(clientService.findById(any())).thenReturn(clientGetDTO);

        mockMvc.perform(get("/client/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(clientGetDTO)));
    }

    @Test
    public void testFindById_NotFound() throws Exception {
        when(clientService.findById(any())).thenReturn(null);

        mockMvc.perform(get("/client/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateClient_Success() throws Exception {
        ClientDTO createdClient = new ClientDTO();
        when(clientService.create(any())).thenReturn(createdClient);

        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ClientCreateDTO())))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createdClient)));
    }

    @Test
    public void testListAll() throws Exception {
        List<ClientGetDTO> allClients = List.of(new ClientGetDTO(), new ClientGetDTO());
        when(clientService.listAll()).thenReturn(allClients);

        mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-Total-Count"))
                .andExpect(content().json(objectMapper.writeValueAsString(allClients)));
    }

    @Test
    public void testDeleteById() throws Exception {
        doNothing().when(clientService).deleteById(any());

        mockMvc.perform(delete("/client/1"))
                .andExpect(status().isNoContent());
    }
}

