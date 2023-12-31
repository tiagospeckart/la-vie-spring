package com.moredevs.psychclinic.services.impl;

import com.moredevs.psychclinic.exceptions.ResourceNotFoundException;
import com.moredevs.psychclinic.models.dtos.*;
import com.moredevs.psychclinic.models.entities.Client;
import com.moredevs.psychclinic.models.entities.Psychologist;
import com.moredevs.psychclinic.models.entities.Session;
import com.moredevs.psychclinic.models.enums.SessionStatus;
import com.moredevs.psychclinic.repositories.ClientRepository;
import com.moredevs.psychclinic.repositories.PsychologistRepository;
import com.moredevs.psychclinic.repositories.SessionRepository;
import com.moredevs.psychclinic.repositories.specs.SessionSpecifications;
import com.moredevs.psychclinic.services.SessionService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.moredevs.psychclinic.utils.constants.ErrorConstants.Session.*;

@Service
public class SessionServiceImpl implements SessionService {
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    PsychologistRepository psychologistRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public SessionDTO create(SessionCreateDTO sessionCreateDTO) {
        return save(sessionCreateDTO);
    }

    @Override
    public SessionDTO save(SessionCreateDTO sessionCreateDTO) {
        try {
            Psychologist psychologist = psychologistRepository.findById(sessionCreateDTO.getPsychologistId())
                    .orElseThrow(() -> new ResourceNotFoundException("Psychologist not found"));

            Client client = clientRepository.findById(sessionCreateDTO.getClientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

            Session session = new Session();
            session.setPsychologist(psychologist);
            session.setClient(client);
            session.setDateAndTime(sessionCreateDTO.getDateAndTime());
            session.setSessionNotes(sessionCreateDTO.getSessionNotes());
            session.setSessionStatus(sessionCreateDTO.getSessionStatus());

            Session savedSession = sessionRepository.save(session);
            SessionDTO sessionReturnDto = mapper.map(savedSession, SessionDTO.class);
            sessionReturnDto.setClientDTO(mapper.map(client, ClientDTO.class));
            sessionReturnDto.setPsychologistDTO(mapper.map(psychologist, PsychologistDTO.class));
            return sessionReturnDto;

        } catch (Exception e) {
            logger.error(SESSION_INSERTION_ERROR, e);
            throw new RuntimeException(SESSION_INSERTION_ERROR);
        }
    }

    @Override
    public boolean completeSessionById(Integer sessionId) {
        try {
            Session session = sessionRepository.findById(Long.valueOf(sessionId))
                    .orElseThrow(() -> new ResourceNotFoundException("Session with ID " + sessionId + " not found"));
            session.setSessionStatus(SessionStatus.COMPLETED);
            sessionRepository.save(session);
            return true;
        } catch (Exception e) {
            logger.error(SESSION_COMPLETION_ERROR, e);
            throw new RuntimeException(SESSION_COMPLETION_ERROR);
        }
    }

    public SessionDTO rescheduleSessionById(Integer sessionId, LocalDateTime newDateTime) {
        try {
            Session session = sessionRepository.findById(Long.valueOf(sessionId))
                    .orElseThrow(() -> new ResourceNotFoundException("Session with ID " + sessionId + " not found"));

            // TODO -> refactor into it's own function
            if (session.getSessionStatus() != SessionStatus.PLANNED) {
                throw new IllegalStateException(SESSION_RESCHEDULE_ONLY_PLANNED);
            }
            session.setDateAndTime(newDateTime);
            sessionRepository.save(session);
            return mapper.map(session, SessionDTO.class);
        } catch (ResourceNotFoundException e) {
            logger.error(SESSION_ID_NOT_FOUND, e);
            throw e;
        } catch (IllegalStateException e) {
            logger.error(SESSION_RESCHEDULE_INVALID_STATE, e);
            throw e;
        } catch (Exception e) {
            logger.error(SESSION_RESCHEDULE_ERROR, e);
            throw new RuntimeException(SESSION_RESCHEDULE_FAILURE);
        }
    }

    @Override
    public boolean cancelSessionById(Integer sessionId) {
        try {
            Session session = sessionRepository.findById(Long.valueOf(sessionId))
                    .orElseThrow(() -> new ResourceNotFoundException("Session with ID " + sessionId + " not found"));
            session.setSessionStatus(SessionStatus.CANCELLED);
            sessionRepository.save(session);
            return true;
        } catch (ResourceNotFoundException e) {
            logger.error("Session not found: ", e);
            throw e;
        } catch (Exception e) {
            logger.error(SESSION_CANCELLATION_ERROR, e);
            throw new RuntimeException(SESSION_CANCELLATION_ERROR);
        }
    }

    @Override
    @Transactional
    public List<SessionGetDTO> listClientSessionsById(Integer clientId) {
        List<Session> sessions = sessionRepository.findAllByClientId(clientId);
        return sessions.stream()
                .map(session -> mapper.map(session, SessionGetDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SessionGetDTO> listPsychologistSessionsById(Integer psychologistId) {
        List<Session> sessions = sessionRepository.findAllByPsychologistId(psychologistId);
        return sessions.stream()
                .map(session -> mapper.map(session, SessionGetDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SessionGetDTO> listClientPsychologistSessionsById(Integer clientId, Integer psychologistId) {
        List<Session> sessions = sessionRepository.findAllByClientIdAndPsychologistId(clientId, psychologistId);
        return sessions.stream()
                .map(session -> mapper.map(session, SessionGetDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SessionDTO update(SessionDTO sessionDTO) {
        if (sessionDTO.getId() == null) {
            throw new RuntimeException(SESSION_ID_NULL);
        }

        Optional<Session> oSession = sessionRepository.findById(Long.valueOf(sessionDTO.getId()));

        if (oSession.isPresent()) {
            Session existingSession = oSession.get();

            if (sessionDTO.getDateAndTime() != null) {
                existingSession.setDateAndTime(sessionDTO.getDateAndTime());
            }

            if (sessionDTO.getSessionNotes() != null && !sessionDTO.getSessionNotes().isEmpty()) {
                existingSession.setSessionNotes(sessionDTO.getSessionNotes());
            }

            if (sessionDTO.getSessionStatus() != null) {
                existingSession.setSessionStatus(sessionDTO.getSessionStatus());
            }

            if (sessionDTO.getUpdatedBy() != null && !sessionDTO.getUpdatedBy().isEmpty()) {
                existingSession.setUpdatedBy(sessionDTO.getUpdatedBy());
            }

            existingSession.setUpdatedAt(LocalDateTime.now());

            try {
                Session updatedSession = sessionRepository.save(existingSession);
                return mapper.map(updatedSession, SessionDTO.class);
            } catch (Exception e) {
                logger.error(SESSION_UPDATE_ERROR, e);
                throw new RuntimeException(SESSION_UPDATE_ERROR);
            }

        } else {
            throw new RuntimeException(SESSION_ID_NOT_FOUND);
        }
    }

    @Override
    public SessionGetDTO findById(Integer id) {
        Optional<Session> oSession = sessionRepository.findById(Long.valueOf(id));
        SessionGetDTO sessionGetDTO = null;

        if (oSession.isPresent()) {
            sessionGetDTO = mapper.map(oSession.get(), SessionGetDTO.class);
        }
        return sessionGetDTO;
    }

    @Override
    public List<SessionGetDTO> listAll() {
        return sessionRepository.findAll().stream()
                .map(session -> mapper.map(session, SessionGetDTO.class)).toList();
    }

    @Override
    public boolean deleteById(Integer id) {
        try {
            sessionRepository.deleteById(Long.valueOf(id));
            return true;
        } catch (Exception e) {
            logger.error("Error deleting session with ID: " + id, e);
            return false;
        }
    }

    public List<SessionInPsychologistListDTO> findSessionsByPsychologistId(Integer psychologistId) {
        Specification<Session> spec = SessionSpecifications.withClientName(psychologistId);
        List<Session> sessions = sessionRepository.findAll(spec);
        return sessions.stream()
                .map(session -> mapper.map(session, SessionInPsychologistListDTO.class))
                .collect(Collectors.toList());
    }
}