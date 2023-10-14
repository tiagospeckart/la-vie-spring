package com.moredevs.psychclinic.services.impl;

import com.moredevs.psychclinic.models.dtos.AdminCreateDTO;
import com.moredevs.psychclinic.models.dtos.AdminDTO;
import com.moredevs.psychclinic.models.entities.Admin;
import com.moredevs.psychclinic.repositories.AdminRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
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
        MockitoAnnotations.initMocks(this); // Initialize Mockito annotations
        adminDTO = new AdminDTO();
        admin = new Admin();
    }

    @Test
    public void testCreateAdmin() {
        AdminCreateDTO adminCreateDTO = new AdminCreateDTO();
        when(mapper.map(adminCreateDTO, Admin.class)).thenReturn(admin);
        when(adminRepository.save(admin)).thenReturn(admin);
        when(mapper.map(admin, AdminDTO.class)).thenReturn(adminDTO);

        AdminDTO result = service.create(adminCreateDTO);

        assertEquals(adminDTO, result);
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    public void testSaveAdmin() {
        AdminCreateDTO adminCreateDTO = new AdminCreateDTO();
        when(mapper.map(adminCreateDTO, Admin.class)).thenReturn(admin);
        when(adminRepository.save(admin)).thenReturn(admin);
        when(mapper.map(admin, AdminDTO.class)).thenReturn(adminDTO);

        AdminDTO result = service.save(adminCreateDTO);

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
