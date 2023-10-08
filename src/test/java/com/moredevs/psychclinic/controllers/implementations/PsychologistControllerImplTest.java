package com.moredevs.psychclinic.controllers.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moredevs.psychclinic.models.dtos.PsychologistCreateDTO;
import com.moredevs.psychclinic.models.dtos.PsychologistDTO;
import com.moredevs.psychclinic.models.dtos.PsychologistGetDTO;
import com.moredevs.psychclinic.services.PsychologistService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @BeforeEach
    public void setUp() {
        samplePsychologistCreateDTO = PsychologistCreateDTO.builder()
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("StrongP@ss123")
                .phone("+1-555-555-5555")
                .licenseNumber("PSY123456")
                .specializationArea("Clinical")
                .biography("Dr. Jane Doe has over 10 years of experience in clinical psychology.")
                .build();

        samplePsychologistDTO = PsychologistDTO.builder()
                .id(1)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("StrongP@ss123")
                .phone("+1-555-555-5555")
                .licenseNumber("PSY123456")
                .specializationArea("Clinical")
                .biography("Dr. Jane Doe has over 10 years of experience in clinical psychology.")
                .build();

        samplePsychologistGetDTO = PsychologistGetDTO.builder()
                .id(1)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("StrongP@ss123")
                .phone("+1-555-555-5555")
                .licenseNumber("PSY123456")
                .specializationArea("Clinical")
                .biography("Dr. Jane Doe has over 10 years of experience in clinical psychology.")
                .build();
    }

    @Test
    public void testFindById() throws Exception {
        when(psychologistService.findById(1)).thenReturn(samplePsychologistGetDTO);

        mockMvc.perform(get("/psychologist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    /*

    @Test
    public void testCreate() throws Exception {
        when(psychologistService.create(any())).thenReturn(samplePsychologistDTO);

        mockMvc.perform(post("/psychologist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(samplePsychologistCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(samplePsychologistDTO))); // Compare JSON content

        verify(psychologistService).create(any(PsychologistCreateDTO.class));
    }

    @Test
    public void testUpdateById() throws Exception {
        PsychologistDTO updatedPsychologist = new PsychologistDTO();
        updatedPsychologist.setId(1);
        // populate other fields

        when(psychologistService.update(any(PsychologistDTO.class))).thenReturn(updatedPsychologist);

        mockMvc.perform(put("/psychologist/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedPsychologist)))
                .andExpect(status().isOk());
    }

    @Test
    public void testListAll() throws Exception {
        // Implement this test method to ensure full test coverage.
    }

    @Test
    public void testDeleteById() throws Exception {
        doNothing().when(psychologistService).deleteById(1);

        mockMvc.perform(delete("/psychologist/1"))
                .andExpect(status().isNoContent());
    }

    */
}

