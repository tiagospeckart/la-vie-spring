package com.moredevs.psychclinic.controllers.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moredevs.psychclinic.models.dtos.PsychologistCreateDTO;
import com.moredevs.psychclinic.models.dtos.PsychologistDTO;
import com.moredevs.psychclinic.models.dtos.PsychologistGetDTO;
import com.moredevs.psychclinic.models.enums.Status;
import com.moredevs.psychclinic.services.PsychologistService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.core.type.TypeReference;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PsychologistControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PsychologistService psychologistService;

    private PsychologistGetDTO samplePsychologistGetDTO;
    private PsychologistCreateDTO samplePsychologistCreateDTO;
    private PsychologistDTO samplePsychologistDTO;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        samplePsychologistGetDTO = createSamplePsychologistGetDTO();
        samplePsychologistDTO = createSamplePsychologistDTO();
        samplePsychologistCreateDTO = createSamplePsychologistCreateDTO();

        mockServiceBehavior();
    }

    private void mockServiceBehavior() {
        when(psychologistService.create(any(PsychologistCreateDTO.class))).thenReturn(samplePsychologistDTO);
        when(psychologistService.findById(1)).thenReturn(samplePsychologistGetDTO);
    }

    private PsychologistGetDTO createSamplePsychologistGetDTO() {
        return samplePsychologistGetDTO = PsychologistGetDTO.builder()
                .id(1)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("StrongP@ss123")
                .phone("+1-555-555-5555")
                .licenseNumber("PSY123456")
                .specializationArea("Clinical")
                .biography("Dr. Jane Doe has over 10 years of experience in clinical psychology.")
                .status(Status.ACTIVE) // Assuming Status is an Enum with ACTIVE as one of its constants
                .sessions(null) // Populate this if needed
                .isDeleted(false)
                .build();
    }

    private PsychologistDTO createSamplePsychologistDTO() {
        return samplePsychologistDTO = PsychologistDTO.builder()
                .id(1)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("StrongP@ss123")
                .phone("+1-555-555-5555")
                .licenseNumber("PSY123456")
                .specializationArea("Clinical")
                .biography("Dr. Jane Doe has over 10 years of experience.")
                .status(Status.ACTIVE)
                .sessions(null) // Assuming List<SessionDTO> - populate if needed
                .isDeleted(false)
                .build();
    }

    private PsychologistCreateDTO createSamplePsychologistCreateDTO() {
        return samplePsychologistCreateDTO = PsychologistCreateDTO.builder()
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("StrongP@ss123")
                .phone("+1-555-555-5555")
                .licenseNumber("PSY123456")
                .specializationArea("Clinical")
                .biography("Dr. Jane Doe has over 10 years of experience.")
                .build();
    }

    @Test
    public void shouldReturnPsychologistWhenIdIsFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/psychologist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        String responseBody = response.getContentAsString();
        PsychologistGetDTO returnedPsychologist = objectMapper.readValue(responseBody, PsychologistGetDTO.class);

        assertThat(returnedPsychologist.getId()).isEqualTo(1);
        assertThat(returnedPsychologist.getName()).isEqualTo("Jane Doe");
        assertThat(returnedPsychologist.getEmail()).isEqualTo("jane.doe@example.com");
        assertThat(returnedPsychologist.getPhone()).isEqualTo("+1-555-555-5555");
        assertThat(returnedPsychologist.getLicenseNumber()).isEqualTo("PSY123456");
        assertThat(returnedPsychologist.getSpecializationArea()).isEqualTo("Clinical");
        assertThat(returnedPsychologist.getBiography()).isEqualTo("Dr. Jane Doe has over 10 years of experience in clinical psychology.");
        assertThat(returnedPsychologist.getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(returnedPsychologist.getIsDeleted()).isEqualTo(false);
    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(post("/psychologist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(samplePsychologistCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(samplePsychologistDTO)));

        verify(psychologistService, times(1)).create(any(PsychologistCreateDTO.class));
    }

    @Test
    public void testUpdateById() throws Exception {
        PsychologistDTO updatedPsychologist = PsychologistDTO.builder()
                .id(1)
                .name("Updated Name")
                .email("updated.email@example.com")
                .password("UpdatedP@ss123")
                .phone("+1-555-555-5555")
                .licenseNumber("UpdatedLicenseNumber")
                .specializationArea("UpdatedSpecialization")
                .biography("Updated biography.")
                .status(Status.ACTIVE)
                .sessions(null)
                .isDeleted(false)
                .build();

        when(psychologistService.update(any(PsychologistDTO.class))).thenReturn(updatedPsychologist);

        mockMvc.perform(put("/psychologist/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPsychologist)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedPsychologist)));

        verify(psychologistService, times(1)).update(any(PsychologistDTO.class));
    }



    @Test
    public void testListAll() throws Exception {
        List<PsychologistGetDTO> allPsychologists = Arrays.asList(
                samplePsychologistGetDTO
        );

        when(psychologistService.listAll()).thenReturn(allPsychologists);

        MvcResult result = mockMvc.perform(get("/psychologist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Total-Count", String.valueOf(allPsychologists.size())))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        List<PsychologistGetDTO> returnedPsychologists = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<PsychologistGetDTO>>() {});

        assertThat(returnedPsychologists.size()).isEqualTo(allPsychologists.size());

        verify(psychologistService, times(1)).listAll();
    }

    @Test
    public void testDeleteById() throws Exception {
        doNothing().when(psychologistService).deleteById(1);

        mockMvc.perform(delete("/psychologist/1"))
                .andExpect(status().isNoContent());
    }
}

