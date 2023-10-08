package com.moredevs.psychclinic.controllers;

import com.moredevs.psychclinic.models.dtos.AdminCreateDTO;
import com.moredevs.psychclinic.models.dtos.AdminDTO;
import com.moredevs.psychclinic.models.dtos.AdminGetDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AdminController {
    ResponseEntity<AdminDTO> create(@Valid @RequestBody AdminCreateDTO adminCreateDTO);
    ResponseEntity<AdminGetDTO> findById(Integer id);
    ResponseEntity<List<AdminGetDTO>> listAll();
    ResponseEntity<AdminDTO> updateById(Integer id, AdminDTO updatingAdminDto);
    ResponseEntity<Void> deleteById(Integer id);

}

