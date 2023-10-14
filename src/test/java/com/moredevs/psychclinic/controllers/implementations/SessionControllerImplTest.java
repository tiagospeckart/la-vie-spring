package com.moredevs.psychclinic.controllers.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.moredevs.psychclinic.models.dtos.*;
import com.moredevs.psychclinic.models.enums.SessionStatus;
import com.moredevs.psychclinic.services.SessionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class SessionControllerImplTest {

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private SessionControllerImpl sessionControllerImpl;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sessionControllerImpl)
                .build();
    }

    @Test
    public void shouldReturnSessionWhenIdIsFound() throws Exception {
        SessionGetDTO sampleSessionGetDTO = createSampleSessionGetDTO();
        when(sessionService.findById(any(Integer.class))).thenReturn(sampleSessionGetDTO);

        MvcResult result = mockMvc.perform(get("/session/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(sampleSessionGetDTO));
    }

    @Test
    public void testCreate() throws Exception {
        SessionCreateDTO sampleSessionCreateDTO = createSampleSessionCreateDTO();
        SessionDTO sampleSessionDTO = createSampleSessionDTO();
        when(sessionService.create(any(SessionCreateDTO.class))).thenReturn(sampleSessionDTO);

        MvcResult result = mockMvc.perform(post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleSessionCreateDTO)))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(sampleSessionDTO));
    }

    @Test
    public void testUpdateById() throws Exception {
        SessionDTO sampleSessionDTO = createSampleSessionDTO();
        when(sessionService.update(any(SessionDTO.class))).thenReturn(sampleSessionDTO);

        MvcResult result = mockMvc.perform(put("/session/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleSessionDTO)))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(sampleSessionDTO));
    }

    // Helper methods for creating sample DTOs

    private SessionGetDTO createSampleSessionGetDTO() {
        return SessionGetDTO.builder()
                .id(1)
                .psychologistName("Dr. John Doe")
                .clientName("Jane Doe")
                .dateAndTime(LocalDateTime.of(2023, 10, 7, 15, 0))
                .sessionNotes("Initial assessment conducted.")
                .sessionStatus(SessionStatus.COMPLETED)
                .build();
    }

    private SessionCreateDTO createSampleSessionCreateDTO() {
        return SessionCreateDTO.builder()
                .psychologistId(1)
                .clientId(2)
                .dateAndTime(LocalDateTime.of(2023, 10, 7, 15, 0))
                .sessionNotes("Initial assessment to be conducted.")
                .sessionStatus(SessionStatus.PLANNED)
                .build();
    }

    private SessionDTO createSampleSessionDTO() {
        PsychologistDTO samplePsychologistDTO = PsychologistDTO.builder()
                .id(1)
                .name("Dr. John Doe")
                .build();

        ClientDTO sampleClientDTO = ClientDTO.builder()
                .id(2)
                .name("Jane Doe")
                .build();

        return SessionDTO.builder()
                .id(1)
                .psychologistDTO(samplePsychologistDTO)
                .clientDTO(sampleClientDTO)
                .dateAndTime(LocalDateTime.of(2023, 10, 7, 15, 0))
                .sessionNotes("Initial assessment conducted.")
                .sessionStatus(SessionStatus.COMPLETED)
                .build();
    }
}
