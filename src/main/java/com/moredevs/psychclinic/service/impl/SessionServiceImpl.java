package com.moredevs.psychclinic.service.impl;

import com.moredevs.psychclinic.exceptions.ResourceNotFoundException;
import com.moredevs.psychclinic.models.dtos.SessionCreateDTO;
import com.moredevs.psychclinic.models.dtos.SessionDTO;
import com.moredevs.psychclinic.models.entities.Client;
import com.moredevs.psychclinic.models.entities.Psychologist;
import com.moredevs.psychclinic.models.entities.Session;
import com.moredevs.psychclinic.repositories.ClientRepository;
import com.moredevs.psychclinic.repositories.PsychologistRepository;
import com.moredevs.psychclinic.repositories.SessionRepository;
import com.moredevs.psychclinic.service.SessionService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

            Session createdSession = sessionRepository.save(session);
            return mapper.map(createdSession, SessionDTO.class);

        } catch (Exception e) {
            logger.error(SESSION_INSERTION_ERROR, e);
            throw new RuntimeException(SESSION_INSERTION_ERROR);
        }
    }

    @Override
    @Transactional
    public SessionDTO update(SessionDTO sessionDTO) {
        if (sessionDTO.getId() == null) {
            throw new RuntimeException(SESSION_NULL_ID);
        }

        Optional<Session> oSession = sessionRepository.findById(sessionDTO.getId());

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
    public SessionDTO findById(Integer id) {
        Optional<Session> oSession = sessionRepository.findById(id);
        SessionDTO sessionDTO = null;

        if (oSession.isPresent()) {
            sessionDTO = mapper.map(oSession.get(), SessionDTO.class);
        }
        return sessionDTO;
    }

    @Override
    public List<SessionDTO> listAll() {
        return sessionRepository.findAll().stream()
                .map(session -> mapper.map(session, SessionDTO.class)).toList();
    }

    @Override
    public void deleteById(Integer id) {
        sessionRepository.deleteById(id);
    }
}