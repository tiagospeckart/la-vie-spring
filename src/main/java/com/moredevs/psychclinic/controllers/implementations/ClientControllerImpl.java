package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.ClientController;
import com.moredevs.psychclinic.models.dtos.ClientCreateDTO;
import com.moredevs.psychclinic.models.dtos.ClientDTO;
import com.moredevs.psychclinic.models.dtos.ClientGetDTO;
import com.moredevs.psychclinic.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/client")
@Tag(name = "Client Management", description = "Endpoints for managing clients")
public class ClientControllerImpl implements ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    @Operation(summary = "Find Client by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client found",
                            content = @Content(schema = @Schema(implementation = ClientDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            })
    @Override
    public ResponseEntity<ClientGetDTO> findById(@Parameter(description = "ID of client to be searched") @PathVariable Integer id) {
        ClientGetDTO clientGetDTO = clientService.findById(id);
        if (clientGetDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientGetDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new Client",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @Override
    public ResponseEntity<ClientDTO> create(@Parameter(description = "Client to add") @RequestBody ClientCreateDTO clientCreateDTO) {
        ClientDTO createdClient = clientService.create(clientCreateDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdClient.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdClient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Client",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Client not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @Override
    public ResponseEntity<ClientDTO> updateById(@Parameter(description = "ID of client to be updated") @PathVariable Integer id,
                                                @org.jetbrains.annotations.NotNull @Parameter(description = "Updated client") @RequestBody ClientDTO updatedClient) {
        updatedClient.setId(id);
        ClientDTO savedClient = clientService.update(updatedClient);
        if (savedClient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(savedClient);
    }

    @GetMapping
    @Operation(summary = "List all Clients",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            })
    @Override
    public ResponseEntity<List<ClientGetDTO>> listAll() {
        List<ClientGetDTO> allClients = clientService.listAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(allClients.size()));
        return ResponseEntity.ok().headers(headers).body(allClients);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Client by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            })
    @Override
    public ResponseEntity<Void> deleteById(@Parameter(description = "ID of client to be deleted") @PathVariable Integer id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
