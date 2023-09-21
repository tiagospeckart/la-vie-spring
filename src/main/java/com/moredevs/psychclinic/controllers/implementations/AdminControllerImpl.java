package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.AdminController;
import com.moredevs.psychclinic.exceptions.NotFoundException;
import com.moredevs.psychclinic.models.dtos.AdminDTO;
import com.moredevs.psychclinic.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminControllerImpl implements AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<AdminDTO> findById(@PathVariable Integer id) {
        AdminDTO adminDTO = adminService.findById(id);
        if (adminDTO == null){
            throw new NotFoundException();
        }
        return ResponseEntity.ok(adminDTO);
    }

    @PostMapping
    @Override
    public ResponseEntity<AdminDTO> create(@Valid @RequestBody AdminDTO adminDTO) {
        AdminDTO createdAdmin = adminService.create(adminDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdAdmin.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdAdmin);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<AdminDTO> updateById(@PathVariable Integer id, @RequestBody AdminDTO updatedAdmin) {
        AdminDTO existingAdmin = adminService.findById(id);

        if (existingAdmin == null) {
            return ResponseEntity.notFound().build();
        }

        updatedAdmin.setId(id);
        AdminDTO savedAdmin = adminService.update(updatedAdmin);

        return ResponseEntity.ok(savedAdmin);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<AdminDTO>> listAll() {
        List<AdminDTO> allItems = adminService.listAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(allItems.size()));
        return ResponseEntity.ok().headers(headers).body(allItems);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        adminService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
