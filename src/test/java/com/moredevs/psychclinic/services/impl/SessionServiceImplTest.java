package com.moredevs.psychclinic.services.impl;

import com.moredevs.psychclinic.models.dtos.*;
import com.moredevs.psychclinic.models.entities.Client;
import com.moredevs.psychclinic.models.entities.Psychologist;
import com.moredevs.psychclinic.models.entities.Session;
import com.moredevs.psychclinic.models.enums.SessionStatus;
import com.moredevs.psychclinic.repositories.ClientRepository;
import com.moredevs.psychclinic.repositories.PsychologistRepository;
import com.moredevs.psychclinic.repositories.SessionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SessionServiceImplTest {

    @InjectMocks
    private SessionServiceImpl sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private PsychologistRepository psychologistRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ModelMapper modelMapper;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        // Arrange
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2023/10/07 00:00", formatter);

        SessionCreateDTO sessionCreateDTO = SessionCreateDTO.builder()
                .psychologistId(1)
                .clientId(2)
                .sessionNotes("Note")
                .dateAndTime(dateTime)
                .sessionStatus(SessionStatus.COMPLETED)
                .build();

        Psychologist psychologist = new Psychologist();
        Client client = new Client();
        Session savedSession = new Session();
        SessionDTO mappedSessionDTO = new SessionDTO();
        ClientDTO mappedClientDTO = new ClientDTO();
        PsychologistDTO mappedPsychologistDTO = new PsychologistDTO();

        when(psychologistRepository.findById(any(Integer.class))).thenReturn(Optional.of(psychologist));
        when(clientRepository.findById(any(Integer.class))).thenReturn(Optional.of(client));
        when(sessionRepository.save(any(Session.class))).thenReturn(savedSession);

        // Mock the mapper's behavior
        when(modelMapper.map(savedSession, SessionDTO.class)).thenReturn(mappedSessionDTO);
        when(modelMapper.map(client, ClientDTO.class)).thenReturn(mappedClientDTO);
        when(modelMapper.map(psychologist, PsychologistDTO.class)).thenReturn(mappedPsychologistDTO);

        // Act
        SessionDTO result = sessionService.save(sessionCreateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(mappedSessionDTO, result); // Ensure the mapped SessionDTO is what's returned
        assertEquals(mappedClientDTO, result.getClientDTO()); // Validate the ClientDTO mapping
        assertEquals(mappedPsychologistDTO, result.getPsychologistDTO()); // Validate the PsychologistDTO mapping
    }

    @Test
    public void testCreateSession_PsychologistNotFound() {
        // Arrange
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2023/10/07 00:00", formatter);

        SessionCreateDTO sessionCreateDTO = SessionCreateDTO.builder()
                .psychologistId(1)
                .clientId(2)
                .sessionNotes("Note")
                .dateAndTime(dateTime)
                .sessionStatus(SessionStatus.COMPLETED)
                .build();

        // Mock behavior to simulate psychologist not found
        when(psychologistRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            sessionService.create(sessionCreateDTO);
        });

        // Verify that no further logic gets executed after the exception
        verify(clientRepository, times(0)).findById(any(Integer.class));
        verify(sessionRepository, times(0)).save(any(Session.class));
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    public void testCompleteSessionById() {
        Session mockSession = new Session();
        when(sessionRepository.findById(1L)).thenReturn(java.util.Optional.of(mockSession));

        boolean result = sessionService.completeSessionById(1);

        verify(sessionRepository).findById(1L);
        verify(sessionRepository).save(mockSession);
        assertTrue(result);
    }


    @Test(expected = RuntimeException.class)
    public void testCompleteSessionById_SessionNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        sessionService.completeSessionById(1);
    }

    @Test
    public void testRescheduleSessionById_Success() {
        // Arrange
        Long sessionId = 1L;
        Session mockSession = new Session();
        mockSession.setSessionStatus(SessionStatus.PLANNED); // Ensuring PLANNED status for success
        LocalDateTime newDateTime = LocalDateTime.now();

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(mockSession));
        when(modelMapper.map(mockSession, SessionDTO.class)).thenReturn(new SessionDTO()); // Assuming you've SessionDTO

        // Act
        SessionDTO result = sessionService.rescheduleSessionById(sessionId.intValue(), newDateTime);

        // Assert
        verify(sessionRepository).findById(sessionId);
        verify(sessionRepository).save(mockSession);
        assertEquals(newDateTime, mockSession.getDateAndTime());
        assertNotNull(result);
    }

    @Test
    public void testCancelSessionById() {
        Session mockSession = new Session();
        when(sessionRepository.findById(1L)).thenReturn(java.util.Optional.of(mockSession));

        boolean result = sessionService.cancelSessionById(1);

        verify(sessionRepository).findById(1L);
        verify(sessionRepository).save(mockSession);
        assertTrue(result);
    }

    @Test
    public void testListClientSessionsById() {
        // Arrange
        Session session1 = new Session();
        Session session2 = new Session();
        List<Session> mockSessions = Arrays.asList(session1, session2);

        SessionGetDTO sessionGetDTO1 = new SessionGetDTO();
        SessionGetDTO sessionGetDTO2 = new SessionGetDTO();
        List<SessionGetDTO> mockDTOs = Arrays.asList(sessionGetDTO1, sessionGetDTO2);

        when(sessionRepository.findAllByClientId(1)).thenReturn(mockSessions);
        when(modelMapper.map(session1, SessionGetDTO.class)).thenReturn(sessionGetDTO1);
        when(modelMapper.map(session2, SessionGetDTO.class)).thenReturn(sessionGetDTO2);

        // Act
        List<SessionGetDTO> result = sessionService.listClientSessionsById(1);

        // Assert
        verify(sessionRepository).findAllByClientId(1);
        verify(modelMapper).map(session1, SessionGetDTO.class);
        verify(modelMapper).map(session2, SessionGetDTO.class);
        assertEquals(mockDTOs, result);
    }

    @Test
    public void testListPsychologistSessionsById() {
        // Arrange
        Session session1 = new Session();
        Session session2 = new Session();
        List<Session> mockSessions = Arrays.asList(session1, session2);

        SessionGetDTO sessionGetDTO1 = new SessionGetDTO();
        SessionGetDTO sessionGetDTO2 = new SessionGetDTO();
        List<SessionGetDTO> mockDTOs = Arrays.asList(sessionGetDTO1, sessionGetDTO2);

        when(sessionRepository.findAllByPsychologistId(1)).thenReturn(mockSessions);
        when(modelMapper.map(session1, SessionGetDTO.class)).thenReturn(sessionGetDTO1);
        when(modelMapper.map(session2, SessionGetDTO.class)).thenReturn(sessionGetDTO2);

        // Act
        List<SessionGetDTO> result = sessionService.listPsychologistSessionsById(1);

        // Assert
        verify(sessionRepository).findAllByPsychologistId(1);
        verify(modelMapper).map(session1, SessionGetDTO.class);
        verify(modelMapper).map(session2, SessionGetDTO.class);
        assertEquals(mockDTOs, result);
    }

    @Test
    public void testListClientPsychologistSessionsById() {
        // Arrange
        Session session1 = new Session();
        Session session2 = new Session();
        List<Session> mockSessions = Arrays.asList(session1, session2);

        SessionGetDTO sessionGetDTO1 = new SessionGetDTO();
        SessionGetDTO sessionGetDTO2 = new SessionGetDTO();
        List<SessionGetDTO> mockDTOs = Arrays.asList(sessionGetDTO1, sessionGetDTO2);

        when(sessionRepository.findAllByClientIdAndPsychologistId(1, 2)).thenReturn(mockSessions);
        when(modelMapper.map(session1, SessionGetDTO.class)).thenReturn(sessionGetDTO1);
        when(modelMapper.map(session2, SessionGetDTO.class)).thenReturn(sessionGetDTO2);

        // Act
        List<SessionGetDTO> result = sessionService.listClientPsychologistSessionsById(1, 2);

        // Assert
        verify(sessionRepository).findAllByClientIdAndPsychologistId(1, 2);
        verify(modelMapper).map(session1, SessionGetDTO.class);
        verify(modelMapper).map(session2, SessionGetDTO.class);
        assertEquals(mockDTOs, result);
    }

    @Test
    public void testUpdate() {
        // Arrange
        SessionDTO mockSessionDTO = new SessionDTO();
        mockSessionDTO.setId(1);
        mockSessionDTO.setDateAndTime(LocalDateTime.now());
        mockSessionDTO.setSessionNotes("Notes");
        mockSessionDTO.setSessionStatus(SessionStatus.PLANNED);
        mockSessionDTO.setUpdatedBy("Updater");

        Session mockSession = new Session();
        mockSession.setId(1);

        SessionDTO updatedMockSessionDTO = new SessionDTO();

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(sessionRepository.save(mockSession)).thenReturn(mockSession);
        when(modelMapper.map(mockSession, SessionDTO.class)).thenReturn(updatedMockSessionDTO);

        // Act
        SessionDTO result = sessionService.update(mockSessionDTO);

        // Assert
        verify(sessionRepository).findById(1L);
        verify(sessionRepository).save(mockSession);
        verify(modelMapper).map(mockSession, SessionDTO.class);
        assertEquals(updatedMockSessionDTO, result);
    }

    @Test
    public void testFindById() {
        // Arrange
        Session mockSession = new Session();
        mockSession.setId(1);
        SessionGetDTO mockSessionGetDTO = new SessionGetDTO();
        mockSessionGetDTO.setId(1);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(modelMapper.map(mockSession, SessionGetDTO.class)).thenReturn(mockSessionGetDTO);

        // Act
        SessionGetDTO result = sessionService.findById(1);

        // Assert
        verify(sessionRepository).findById(1L);
        verify(modelMapper).map(mockSession, SessionGetDTO.class);
        assertEquals(mockSessionGetDTO, result);
    }

    @Test
    public void testListAll() {
        // Arrange
        List<Session> mockSessions = Arrays.asList(new Session(), new Session());
        List<SessionGetDTO> mockSessionDTOs = Arrays.asList(new SessionGetDTO(), new SessionGetDTO());

        when(sessionRepository.findAll()).thenReturn(mockSessions);
        when(modelMapper.map(any(Session.class), eq(SessionGetDTO.class))).thenReturn(mockSessionDTOs.get(0), mockSessionDTOs.get(1));

        // Act
        List<SessionGetDTO> result = sessionService.listAll();

        // Assert
        verify(sessionRepository).findAll();
        assertEquals(mockSessionDTOs, result);
    }

    @Test
    public void testDeleteById() {
        // Arrange
        doNothing().when(sessionRepository).deleteById(1L);

        // Act
        boolean result = sessionService.deleteById(1);

        // Assert
        verify(sessionRepository).deleteById(1L);
        assertEquals(true, result);
    }
}

