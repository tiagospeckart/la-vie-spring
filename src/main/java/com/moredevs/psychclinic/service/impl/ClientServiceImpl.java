package com.moredevs.psychclinic.service.impl;

import com.moredevs.psychclinic.models.dtos.ClientDTO;
import com.moredevs.psychclinic.models.entities.Client;
import com.moredevs.psychclinic.repository.AdminRepository;
import com.moredevs.psychclinic.repository.ClientRepository;
import com.moredevs.psychclinic.service.ClientService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public ClientDTO create(ClientDTO clientDTO) {
        return save(clientDTO);
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        try {
            Client client = mapper.map(clientDTO, Client.class);
            Client createdClient = clientRepository.save(client);
            return mapper.map(createdClient, ClientDTO.class);
        } catch (Exception e) {
            logger.error("An error occurred while inserting Client", e);
            throw new RuntimeException("CLIENT_INSERTION_ERROR");
        }
    }

    @Override
    public ClientDTO update(ClientDTO clientDTO) {
        if (clientDTO.getId() == null) {
            throw new RuntimeException("CLIENT_EMPTY_ID");
        }

        Optional<Client> oClient = clientRepository.findById(clientDTO.getId());

        if (oClient.isPresent()) {
            Client existingClient = oClient.get();

            // Field-level update and validation
            if (clientDTO.getAddress() != null && !clientDTO.getAddress().isEmpty()) {
                existingClient.setAddress(clientDTO.getAddress());
            }

            if (clientDTO.getPhone() != null && !clientDTO.getPhone().isEmpty()) {
                existingClient.setPhone(clientDTO.getPhone());
            }

            if (clientDTO.getEmail() != null && !clientDTO.getEmail().isEmpty()) {
                existingClient.setEmail(clientDTO.getEmail());
            }

            if (clientDTO.getDateOfBirth() != null) {
                existingClient.setDateOfBirth(clientDTO.getDateOfBirth());
            }

            if (clientDTO.getStatus() != null) {
                existingClient.setStatus(clientDTO.getStatus());
            }

            if (clientDTO.getObservations() != null && !clientDTO.getObservations().isEmpty()) {
                existingClient.setObservations(clientDTO.getObservations());
            }

            if (clientDTO.getSessions() != null && !clientDTO.getSessions().isEmpty()) {
                existingClient.setSessions(clientDTO.getSessions()); // Assume you have setters for this or use a more complex merging strategy
            }

            if (clientDTO.getUpdatedBy() != null && !clientDTO.getUpdatedBy().isEmpty()) {
                existingClient.setUpdatedBy(clientDTO.getUpdatedBy());
            }

            // Update the updatedAt field
            existingClient.setUpdatedAt(LocalDateTime.now());

        } else {
            throw new RuntimeException("CLIENT_ID_NOT_FOUND");
        }
    }

    @Override
    public ClientDTO findById(Integer id) {
        Optional<Client> oClient = clientRepository.findById(id);
        ClientDTO clientDTO = null;

        if (oClient.isPresent()) {
            clientDTO = mapper.map(oClient.get(), ClientDTO.class);
        }
        return clientDTO;
    }

    @Override
    public List<ClientDTO> listAll() {
        return clientRepository.findAll().stream()
                .map(client -> mapper.map(client, ClientDTO.class)).toList();
    }

    @Override
    public void deleteById(Integer id) {
        clientRepository.deleteById(id);
    }
}