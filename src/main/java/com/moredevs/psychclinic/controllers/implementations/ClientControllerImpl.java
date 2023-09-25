package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.ClientController;
import com.moredevs.psychclinic.models.dtos.ClientDTO;
import com.moredevs.psychclinic.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/client")
public class ClientControllerImpl implements ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ClientDTO> findById(@PathVariable Integer id) {
        ClientDTO clientDTO = clientService.findById(id);
        if (clientDTO == null) {
            return ResponseEntity.notFound().build();  // Returning a 404 if not found
        }
        return ResponseEntity.ok(clientDTO);
    }

    @PostMapping
    @Override
    public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO clientDTO) {
        ClientDTO createdClient = clientService.create(clientDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdClient.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdClient);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ClientDTO> updateById(@PathVariable Integer id, @RequestBody ClientDTO updatedClient) {
        updatedClient.setId(id);
        ClientDTO savedClient = clientService.update(updatedClient);
        if (savedClient == null) {
            return ResponseEntity.notFound().build();  // Returning a 404 if update operation failed
        }
        return ResponseEntity.ok(savedClient);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<ClientDTO>> listAll() {
        List<ClientDTO> allClients = clientService.listAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(allClients.size()));
        return ResponseEntity.ok().headers(headers).body(allClients);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();  // Returning a 204, no content if delete is successful
    }
}