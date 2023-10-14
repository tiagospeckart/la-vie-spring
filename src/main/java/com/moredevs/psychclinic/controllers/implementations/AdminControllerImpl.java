package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.AdminController;
import com.moredevs.psychclinic.exceptions.NotFoundException;
import com.moredevs.psychclinic.models.dtos.AdminCreateDTO;
import com.moredevs.psychclinic.models.dtos.AdminDTO;
import com.moredevs.psychclinic.models.dtos.AdminGetDTO;
import com.moredevs.psychclinic.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
@Tag(name = "Admin Management", description = "Endpoints for managing admins")
public class AdminControllerImpl implements AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Find Admin by ID", description = "Returns a single admin",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(implementation = AdminDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Admin not found")
            })
    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<AdminGetDTO> findById(@PathVariable Integer id) {
        AdminGetDTO adminGetDTO = adminService.findById(id);
        if (adminGetDTO == null) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(adminGetDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new Admin",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Admin created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @Override
    public ResponseEntity<AdminDTO> create(@Valid @RequestBody AdminCreateDTO adminCreateDTO) {
        AdminDTO createdAdmin = adminService.create(adminCreateDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdAdmin.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdAdmin);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Admin",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Admin not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @Override
    public ResponseEntity<AdminDTO> updateById(@PathVariable Integer id, @RequestBody AdminDTO updatedAdmin) {
        if (updatedAdmin == null) {
            return ResponseEntity.badRequest().build();
        }

        AdminGetDTO existingAdmin = adminService.findById(id);

        if (existingAdmin == null) {
            return ResponseEntity.notFound().build();
        }

        updatedAdmin.setId(id);
        AdminDTO savedAdmin = adminService.update(updatedAdmin);

        return ResponseEntity.ok(savedAdmin);
    }

    @GetMapping
    @Operation(summary = "List all Admins",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation")
            })
    @Override
    public ResponseEntity<List<AdminGetDTO>> listAll() {
        List<AdminGetDTO> allItems = adminService.listAll();
        HttpHeaders headers = new HttpHeaders();
        if (allItems == null) {
            allItems = Collections.emptyList();
        }
        headers.add("X-Total-Count", String.valueOf(allItems.size()));
        return ResponseEntity.ok().headers(headers).body(allItems);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete an Admin by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Admin not found")
            })
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        adminService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
