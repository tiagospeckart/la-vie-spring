package com.moredevs.psychclinic.services.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import com.moredevs.psychclinic.models.dtos.AdminDTO;
import com.moredevs.psychclinic.models.entities.Admin;
import com.moredevs.psychclinic.repositories.AdminRepository;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceImplTest {
    @Mock
    private AdminRepository adminRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private AdminServiceImpl service;

    private AdminDTO adminDTO;
    private Admin admin;

    @Before
    public void setup() {
        adminDTO = new AdminDTO();
        admin = new Admin();
    }

    @Test
    public void testCreateAdmin() {
        when(mapper.map(adminDTO, Admin.class)).thenReturn(admin);
        when(adminRepository.save(admin)).thenReturn(admin);
        when(mapper.map(admin, AdminDTO.class)).thenReturn(adminDTO);

        AdminDTO result = service.create(adminDTO);

        assertEquals(adminDTO, result);
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    public void testSaveAdmin() {
        when(mapper.map(adminDTO, Admin.class)).thenReturn(admin);
        when(adminRepository.save(admin)).thenReturn(admin);
        when(mapper.map(admin, AdminDTO.class)).thenReturn(adminDTO);

        AdminDTO result = service.save(adminDTO);

        assertEquals(adminDTO, result);
        verify(adminRepository, times(1)).save(admin);
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateAdminWithEmptyId() {
        adminDTO.setId(null);
        service.update(adminDTO);
    }

    @Test
    public void testUpdateAdmin() {
        adminDTO.setId(1);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        admin.setEmail("new_email@example.com");
        adminDTO.setEmail("new_email@example.com");

        when(adminRepository.save(admin)).thenReturn(admin);
        when(mapper.map(admin, AdminDTO.class)).thenReturn(adminDTO);

        AdminDTO result = service.update(adminDTO);

        assertEquals(adminDTO, result);
        verify(adminRepository, times(1)).save(admin);
    }
}
