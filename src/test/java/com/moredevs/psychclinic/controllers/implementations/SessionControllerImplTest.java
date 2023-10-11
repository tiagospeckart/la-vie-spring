package com.moredevs.psychclinic.controllers.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.moredevs.psychclinic.models.dtos.*;
import com.moredevs.psychclinic.models.enums.SessionStatus;
import com.moredevs.psychclinic.services.SessionService;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    private SessionGetDTO sampleSessionGetDTO;
    private SessionCreateDTO sampleSessionCreateDTO;
    private SessionDTO sampleSessionDTO;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        sampleSessionGetDTO = createSampleSessionGetDTO();
        sampleSessionCreateDTO = createSampleSessionCreateDTO();
        sampleSessionDTO = createSampleSessionDTO();

        mockServiceBehavior();
    }

    private void mockServiceBehavior() {
        when(sessionService.findById(any(Integer.class))).thenReturn(sampleSessionGetDTO);
        when(sessionService.create(any(SessionCreateDTO.class))).thenReturn(sampleSessionDTO);
        when(sessionService.update(any(SessionDTO.class))).thenReturn(sampleSessionDTO);
    }

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


    @Test
    public void shouldReturnSessionWhenIdIsFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/session/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(sampleSessionGetDTO));
    }

    @Test
    public void testCreate() throws Exception {
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
        // Assuming your update URL and HTTP method
        MvcResult result = mockMvc.perform(put("/session/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleSessionDTO)))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(sampleSessionDTO));
    }
}