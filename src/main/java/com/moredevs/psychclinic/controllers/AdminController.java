package com.moredevs.psychclinic.controllers;

import com.moredevs.psychclinic.models.dtos.AdminDTO;
import com.moredevs.psychclinic.models.entities.Admin;
import org.springframework.http.ResponseEntity;

public interface AdminController extends Controller<AdminDTO> {
    ResponseEntity<AdminDTO> create(AdminDTO adminDTO);
}
