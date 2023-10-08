package com.moredevs.psychclinic.services.impl;

import com.moredevs.psychclinic.exceptions.ResourceNotFoundException;
import com.moredevs.psychclinic.models.dtos.ClientCreateDTO;
import com.moredevs.psychclinic.models.dtos.ClientDTO;
import com.moredevs.psychclinic.models.dtos.ClientGetDTO;
import com.moredevs.psychclinic.models.dtos.SessionDTO;
import com.moredevs.psychclinic.models.entities.Client;
import com.moredevs.psychclinic.models.entities.Session;
import com.moredevs.psychclinic.repositories.ClientRepository;
import com.moredevs.psychclinic.services.ClientService;
import com.moredevs.psychclinic.utils.EntityUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.moredevs.psychclinic.utils.constants.ErrorConstants.Client.*;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public ClientDTO create(ClientCreateDTO clientCreateDTO) {
        return save(clientCreateDTO);
    }

    @Override
    public ClientDTO save(ClientCreateDTO clientCreateDTO) {
        try {
            Client client = mapper.map(clientCreateDTO, Client.class);
            Client createdClient = clientRepository.save(client);
            return mapper.map(createdClient, ClientDTO.class);
        } catch (Exception e) {
            logger.error(CLIENT_INSERTION_ERROR, e);
            throw new RuntimeException(CLIENT_INSERTION_ERROR);
        }
    }

    @Override
    @Transactional
    public ClientDTO update(ClientDTO clientDTO) {
        if (clientDTO.getId() == null) {
            throw new RuntimeException(CLIENT_NULL_ID);
        }

        Optional<Client> oClient = clientRepository.findById(clientDTO.getId());

        if (oClient.isPresent()) {
            Client existingClient = oClient.get();

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

            List<Session> mergedSessions = null;
            if (clientDTO.getSessions() != null && !clientDTO.getSessions().isEmpty()) {
                mergedSessions = EntityUtils.mergeAndUpdateSessions(
                        existingClient.getSessions(),
                        clientDTO.getSessions(),
                        SessionDTO::getId,
                        Session::getId
                );
                existingClient.setSessions(mergedSessions);
            }

            if (clientDTO.getUpdatedBy() != null && !clientDTO.getUpdatedBy().isEmpty()) {
                existingClient.setUpdatedBy(clientDTO.getUpdatedBy());
            }
            existingClient.setUpdatedAt(LocalDateTime.now());

            try {
                Client updatedClient = clientRepository.save(existingClient);
                return mapper.map(updatedClient, ClientDTO.class);
            } catch (Exception e) {
                logger.error(CLIENT_UPDATE_ERROR, e);
                throw new RuntimeException(CLIENT_UPDATE_ERROR);
            }
        } else {
            throw new RuntimeException(CLIENT_ID_NOT_FOUND);
        }
    }

    @Override
    public ClientGetDTO findById(Integer id) {
        Optional<Client> oClient = clientRepository.findById(id);
        ClientGetDTO clientGetDTO = null;

        if (oClient.isPresent()) {
            clientGetDTO = mapper.map(oClient.get(), ClientGetDTO.class);
        }
        return clientGetDTO;
    }

    @Override
    public List<ClientGetDTO> listAll() {
        return clientRepository.findAllActiveClients().stream()
                .map(client -> mapper.map(client, ClientGetDTO.class)).toList();
    }

    @Override
    public void deleteById(Integer id) {
        softDeleteClientById(id);
    }

    @Transactional
    public void softDeleteClientById(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if(optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setIsDeleted(true);
            clientRepository.save(client);
        } else {
            throw new ResourceNotFoundException("Psychologist with ID " + id + " not found");
        }
    }
}