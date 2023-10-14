package com.moredevs.psychclinic.services.impl;

import com.moredevs.psychclinic.models.dtos.ClientCreateDTO;
import com.moredevs.psychclinic.models.dtos.ClientDTO;
import com.moredevs.psychclinic.models.entities.Client;
import com.moredevs.psychclinic.repositories.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static com.moredevs.psychclinic.utils.constants.ErrorConstants.Client.CLIENT_INSERTION_ERROR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class ClientServiceImplTest {

    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository mockClientRepository;

    @Mock
    private ModelMapper mockMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        clientService = new ClientServiceImpl();
        clientService.clientRepository = mockClientRepository;
        clientService.mapper = mockMapper;
    }

    @Test
    public void testCreate() {
        ClientCreateDTO creatingClient = new ClientCreateDTO();
        ClientDTO clientDTO = new ClientDTO();
        Client client = new Client();

        when(mockMapper.map(creatingClient, Client.class)).thenReturn(client);
        when(mockClientRepository.save(client)).thenReturn(client);
        when(mockMapper.map(client, ClientDTO.class)).thenReturn(clientDTO);

        ClientDTO result = clientService.create(creatingClient);

        assertNotNull(result);
        assertEquals(clientDTO, result);
    }

    @Test(expected = RuntimeException.class)
    public void testCreate_failure() {
        ClientCreateDTO creatingClient = new ClientCreateDTO();

        when(mockMapper.map(creatingClient, Client.class)).thenThrow(new RuntimeException(CLIENT_INSERTION_ERROR));

        clientService.create(creatingClient);
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
