package com.moredevs.psychclinic.services;

import com.moredevs.psychclinic.models.dtos.ClientCreateDTO;
import com.moredevs.psychclinic.models.dtos.ClientDTO;
import com.moredevs.psychclinic.models.dtos.ClientGetDTO;

import java.util.List;

public interface ClientService {
    ClientDTO create(ClientCreateDTO clientDTO);
    ClientDTO save(ClientCreateDTO clientDTO);
    ClientGetDTO findById(Integer id);
    List<ClientGetDTO> listAll();
    ClientDTO update(ClientDTO updatingClientDTO);
    void deleteById(Integer id);
}
