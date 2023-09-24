package com.moredevs.psychclinic.controllers;

import com.moredevs.psychclinic.models.dtos.ClientDTO;
import org.springframework.http.ResponseEntity;

public interface ClientController extends Controller<ClientDTO> {
    ResponseEntity<ClientDTO> create(ClientDTO clientDTO);
}
