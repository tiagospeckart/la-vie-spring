package com.moredevs.psychclinic.controllers.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moredevs.psychclinic.models.dtos.PsychologistCreateDTO;
import com.moredevs.psychclinic.models.dtos.PsychologistDTO;
import com.moredevs.psychclinic.models.dtos.PsychologistGetDTO;
import com.moredevs.psychclinic.models.enums.Status;
import com.moredevs.psychclinic.services.PsychologistService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class PsychologistControllerImplTest {

    @InjectMocks
    private PsychologistControllerImpl psychologistControllerImpl;

    @Mock
    private PsychologistService psychologistService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private PsychologistGetDTO samplePsychologistGetDTO;
    private PsychologistCreateDTO samplePsychologistCreateDTO;
    private PsychologistDTO samplePsychologistDTO;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(psychologistControllerImpl).build();
        samplePsychologistGetDTO = createSamplePsychologistGetDTO();
        samplePsychologistDTO = createSamplePsychologistDTO();
        samplePsychologistCreateDTO = createSamplePsychologistCreateDTO();
        mockServiceBehavior();
    }

    private void mockServiceBehavior() {
        when(psychologistService.create(any(PsychologistCreateDTO.class))).thenReturn(samplePsychologistDTO);
        when(psychologistService.findById(1)).thenReturn(samplePsychologistGetDTO);
        when(psychologistService.update(any(PsychologistDTO.class))).thenReturn(samplePsychologistDTO);
        doNothing().when(psychologistService).deleteById(1);
        when(psychologistService.listAll()).thenReturn(Arrays.asList(samplePsychologistGetDTO));
    }

    private PsychologistGetDTO createSamplePsychologistGetDTO() {
        return PsychologistGetDTO.builder()
                .id(1)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("StrongP@ss123")
                .phone("+1-555-555-5555")
                .licenseNumber("PSY123456")
                .specializationArea("Clinical")
                .biography("Dr. Jane Doe has over 10 years of experience in clinical psychology.")
                .status(Status.ACTIVE)
                .sessions(null)
                .isDeleted(false)
                .build();
    }

    private PsychologistDTO createSamplePsychologistDTO() {
        return PsychologistDTO.builder()
                .id(1)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("StrongP@ss123")
                .phone("+1-555-555-5555")
                .licenseNumber("PSY123456")
                .specializationArea("Clinical")
                .biography("Dr. Jane Doe has over 10 years of experience.")
                .status(Status.ACTIVE)
                .sessions(null)
                .isDeleted(false)
                .build();
    }

    private PsychologistCreateDTO createSamplePsychologistCreateDTO() {
        return PsychologistCreateDTO.builder()
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
        mockMvc.perform(put("/psychologist/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(samplePsychologistDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(samplePsychologistDTO)));

        verify(psychologistService, times(1)).update(any(PsychologistDTO.class));
    }

    @Test
    public void testListAll() throws Exception {
        MvcResult result = mockMvc.perform(get("/psychologist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        String responseBody = response.getContentAsString();
        List<PsychologistGetDTO> returnedPsychologists = objectMapper.readValue(responseBody, new TypeReference<List<PsychologistGetDTO>>() {});

        assertThat(returnedPsychologists.size()).isEqualTo(1);

        verify(psychologistService, times(1)).listAll();
    }

    @Test
    public void testDeleteById() throws Exception {
        mockMvc.perform(delete("/psychologist/1"))
                .andExpect(status().isNoContent());

        verify(psychologistService, times(1)).deleteById(1);
    }
}
