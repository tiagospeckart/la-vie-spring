package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.AdminController;
import com.moredevs.psychclinic.controllers.Controller;
import com.moredevs.psychclinic.exceptions.NotFoundException;
import com.moredevs.psychclinic.models.dtos.AdminDTO;
import com.moredevs.psychclinic.models.entities.Admin;
import com.moredevs.psychclinic.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminControllerImpl implements AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public AdminDTO findById(int id) {
        return null;
    }

    @Override
    public List<AdminDTO> listAll() {
        return null;
    }

    @Override
    public void save(AdminDTO entity) throws NotFoundException {

    }

    @Override
    public void delete(int id) {

    }
}
