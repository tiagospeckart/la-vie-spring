package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.ClientController;
import com.moredevs.psychclinic.exceptions.NotFoundException;
import com.moredevs.psychclinic.models.dtos.ClientDTO;
import com.moredevs.psychclinic.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientControllerImpl implements ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ClientDTO findById(int id) {
        return null;
    }

    @Override
    public List<ClientDTO> listAll() {
        return null;
    }

    @Override
    public void save(ClientDTO entity) throws NotFoundException {

    }

    @Override
    public void delete(int id) {

    }
}
