package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.ClientController;
import com.moredevs.psychclinic.exceptions.NotFoundException;
import com.moredevs.psychclinic.models.dtos.ClientDTO;
import com.moredevs.psychclinic.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ClientControllerImpl implements ClientController {


    @Override
    public ResponseEntity<ClientDTO> findById(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<ClientDTO> create(ClientDTO clientDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ClientDTO> updateById(Integer id, ClientDTO clientDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<ClientDTO>> listAll() {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteById(Integer id) {
        return null;
    }
}
