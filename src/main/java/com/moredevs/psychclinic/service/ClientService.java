package com.moredevs.psychclinic.service;

import com.moredevs.psychclinic.models.dtos.ClientDTO;

public interface ClientService extends Service<ClientDTO> {
    public ClientDTO create(ClientDTO clientDTO);
    public ClientDTO save(ClientDTO clientDTO);
}
