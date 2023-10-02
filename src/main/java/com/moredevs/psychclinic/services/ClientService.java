package com.moredevs.psychclinic.services;

import com.moredevs.psychclinic.models.dtos.ClientDTO;

public interface ClientService extends Service<ClientDTO> {
    public ClientDTO create(ClientDTO clientDTO);
    public ClientDTO save(ClientDTO clientDTO);
}
