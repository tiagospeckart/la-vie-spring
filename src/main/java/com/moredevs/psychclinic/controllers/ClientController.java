package com.moredevs.psychclinic.controllers;

import com.moredevs.psychclinic.models.dtos.ClientCreateDTO;
import com.moredevs.psychclinic.models.dtos.ClientDTO;
import com.moredevs.psychclinic.models.dtos.ClientGetDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClientController {
    ResponseEntity<ClientDTO> create(ClientCreateDTO clientCreateDTO);

    ResponseEntity<ClientGetDTO> findById(Integer id);

    ResponseEntity<List<ClientGetDTO>> listAll();

    ResponseEntity<ClientDTO> updateById(Integer id, ClientDTO updatingClientDTO);

    ResponseEntity<Void> deleteById(Integer id);
}
