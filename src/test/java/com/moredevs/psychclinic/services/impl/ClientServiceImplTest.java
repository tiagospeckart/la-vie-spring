package com.moredevs.psychclinic.services.impl;

import com.moredevs.psychclinic.models.dtos.ClientDTO;
import com.moredevs.psychclinic.models.entities.Client;
import com.moredevs.psychclinic.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static com.moredevs.psychclinic.utils.constants.ErrorConstants.Client.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceImplTest {

    private ClientServiceImpl clientService;
    private ClientRepository mockClientRepository;
    private ModelMapper mockMapper;

    @BeforeEach
    public void setUp() {
        mockClientRepository = mock(ClientRepository.class);
        mockMapper = mock(ModelMapper.class);
        clientService = new ClientServiceImpl();

        clientService.clientRepository = mockClientRepository;
        clientService.mapper = mockMapper;
    }

    @Test
    public void testCreate() {
        ClientDTO clientDTO = new ClientDTO();
        Client client = new Client();

        when(mockMapper.map(clientDTO, Client.class)).thenReturn(client);
        when(mockClientRepository.save(client)).thenReturn(client);
        when(mockMapper.map(client, ClientDTO.class)).thenReturn(clientDTO);

        ClientDTO result = clientService.create(clientDTO);

        assertNotNull(result);
        assertEquals(clientDTO, result);
    }

    @Test
    public void testCreate_failure() {
        ClientDTO clientDTO = new ClientDTO();

        when(mockMapper.map(clientDTO, Client.class)).thenThrow(new RuntimeException(CLIENT_INSERTION_ERROR));

        assertThrows(RuntimeException.class, () -> clientService.create(clientDTO));
    }

    @Test
    public void testUpdate_existingClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1);
        Client existingClient = new Client();
        when(mockClientRepository.findById(1)).thenReturn(Optional.of(existingClient));


        Client updatedClient = new Client();

        when(mockClientRepository.save(existingClient)).thenReturn(updatedClient);
        when(mockMapper.map(updatedClient, ClientDTO.class)).thenReturn(clientDTO);

        ClientDTO result = clientService.update(clientDTO);

        assertNotNull(result);
        assertEquals(clientDTO, result);
    }

}
