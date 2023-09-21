package com.moredevs.psychclinic.service.impl;

import com.moredevs.psychclinic.models.dtos.AdminDTO;
import com.moredevs.psychclinic.models.entities.Admin;
import com.moredevs.psychclinic.repository.AdminRepository;
import com.moredevs.psychclinic.service.AdminService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.moredevs.psychclinic.utils.constants.ErrorConstants.Admin.*;

@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public AdminDTO create(AdminDTO adminDTO) throws RuntimeException {
        return save(adminDTO);
    }

    @Override
    public AdminDTO save(AdminDTO adminDTO) throws RuntimeException {
        try {
            Admin admin = mapper.map(adminDTO, Admin.class);
            Admin createdAdmin = adminRepository.save(admin);
            return mapper.map(createdAdmin, AdminDTO.class);
        } catch (Exception e) {
            logger.error("An error occurred while inserting Admin", e);
            throw new RuntimeException(ADMIN_INSERTION_ERROR);
        }
    }

    @Override
    public AdminDTO update(AdminDTO adminDTO) throws RuntimeException {
        if (adminDTO.getId() == null) {
            throw new RuntimeException(ADMIN_EMPTY_ID);
        }

        Optional<Admin> oAdmin = adminRepository.findById(adminDTO.getId());

        if (oAdmin.isPresent()) {
            Admin existingAdmin = oAdmin.get();

            if (adminDTO.getEmail() != null && !adminDTO.getEmail().isEmpty()) {
                existingAdmin.setEmail(adminDTO.getEmail());
            }

            if (adminDTO.getPhone() != null && !adminDTO.getPhone().isEmpty()) {
                existingAdmin.setPhone(adminDTO.getPhone());
            }

            if (adminDTO.getStatus() != null) {
                existingAdmin.setStatus(adminDTO.getStatus());
            }

            if (adminDTO.getPassword() != null && !adminDTO.getPassword().isEmpty()) {
                existingAdmin.setPassword(adminDTO.getPassword());
            }

            if (adminDTO.getUpdatedBy() != null && !adminDTO.getUpdatedBy().isEmpty()) {
                existingAdmin.setUpdatedBy(adminDTO.getUpdatedBy());
            }

            Admin updatedAdmin = adminRepository.save(existingAdmin);

            return mapper.map(updatedAdmin, AdminDTO.class);
        } else {
            throw new RuntimeException(ADMIN_ID_NOT_FOUND);
        }
    }

    @Override
    public AdminDTO findById(Integer id) {
        Optional<Admin> oAdmin = adminRepository.findById(id);
        AdminDTO adminDTO = null;

        if (oAdmin.isPresent()) {
            adminDTO = mapper.map(oAdmin.get(), AdminDTO.class);
        }
        return adminDTO;
    }

    @Override
    public List<AdminDTO> listAll() {
        return adminRepository.findAll().stream()
                .map(admin -> mapper.map(admin, AdminDTO.class)).toList();
    }

    @Override
    public void deleteById(Integer id) {
        adminRepository.deleteById(id);
    }
}
