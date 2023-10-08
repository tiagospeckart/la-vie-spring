package com.moredevs.psychclinic.services.impl;

import com.moredevs.psychclinic.models.dtos.PsychologistCreateDTO;
import com.moredevs.psychclinic.models.dtos.PsychologistDTO;
import com.moredevs.psychclinic.models.dtos.PsychologistGetDTO;
import com.moredevs.psychclinic.models.entities.Psychologist;
import com.moredevs.psychclinic.repositories.PsychologistRepository;
import com.moredevs.psychclinic.services.SessionService;
import com.moredevs.psychclinic.utils.constants.ErrorConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PsychologistServiceImplTest {

    @Mock
    private PsychologistRepository psychologistRepository;

    @Mock
    private SessionService sessionService;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private PsychologistServiceImpl psychologistService;

    @Test
    public void testCreate() {
        // Arrange
        PsychologistCreateDTO createDTO = new PsychologistCreateDTO();
        Psychologist psychologist = new Psychologist();
        PsychologistDTO psychologistDTO = new PsychologistDTO();

        // Mock behaviors
        when(mapper.map(createDTO, Psychologist.class)).thenReturn(psychologist);
        when(psychologistRepository.save(psychologist)).thenReturn(psychologist);
        when(mapper.map(psychologist, PsychologistDTO.class)).thenReturn(psychologistDTO);

        // Act
        PsychologistDTO result = null;
        try {
            result = psychologistService.create(createDTO);
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(psychologistDTO, result);
        verify(psychologistRepository).save(psychologist);
    }

    @Test
    public void testUpdate_NullId() {
        // Arrange
        PsychologistDTO dto = new PsychologistDTO();

        // Act and Assert
        Exception exception = assertThrows(RuntimeException.class, () -> psychologistService.update(dto));
        assertEquals(ErrorConstants.Psychologist.PSYCHOLOGIST_NULL_ID, exception.getMessage());
    }

    @Test
    public void testFindById_IdNotFound() {
        // Arrange
        int id = 1;
        when(psychologistRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        PsychologistGetDTO result = psychologistService.findById(id);

        // Assert
        assertNull(result);
    }

    @Test
    public void testDeleteById() {
        // Arrange
        int id = 1;
        Psychologist psychologist = new Psychologist();
        when(psychologistRepository.findById(id)).thenReturn(Optional.of(psychologist));

        // Act
        psychologistService.deleteById(id);

        // Assert
        assertTrue(psychologist.getIsDeleted());
        verify(psychologistRepository).save(psychologist);
    }

}

