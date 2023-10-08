package com.moredevs.psychclinic.services.impl;

import com.moredevs.psychclinic.exceptions.ResourceNotFoundException;
import com.moredevs.psychclinic.models.dtos.*;
import com.moredevs.psychclinic.models.entities.Psychologist;
import com.moredevs.psychclinic.models.entities.Session;
import com.moredevs.psychclinic.repositories.PsychologistRepository;
import com.moredevs.psychclinic.repositories.SessionRepository;
import com.moredevs.psychclinic.services.PsychologistService;
import com.moredevs.psychclinic.services.SessionService;
import com.moredevs.psychclinic.utils.EntityUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.moredevs.psychclinic.utils.constants.ErrorConstants.Psychologist.*;

@Service
@Transactional
public class PsychologistServiceImpl implements PsychologistService {

    private static final Logger logger = LoggerFactory.getLogger(PsychologistService.class);

    @Autowired
    PsychologistRepository psychologistRepository;

    @Autowired
    SessionService sessionService;

    @Autowired
    ModelMapper mapper;

    @Override
    public PsychologistDTO create(PsychologistCreateDTO psychologistCreateDTO) {
        return save(psychologistCreateDTO);
    }

    @Override
    public PsychologistDTO save(PsychologistCreateDTO psychologistCreateDTO) {
        try {
            Psychologist psychologist = mapper.map(psychologistCreateDTO, Psychologist.class);
            Psychologist createdPsychologist = psychologistRepository.save(psychologist);
            return mapper.map(createdPsychologist, PsychologistDTO.class);
        } catch (Exception e) {
            logger.error(PSYCHOLOGIST_INSERTION_ERROR, e);
            throw new RuntimeException(PSYCHOLOGIST_INSERTION_ERROR);
        }
    }

    @Override
    public PsychologistDTO update(PsychologistDTO psychologistDTO) {
        if (psychologistDTO.getId() == null) {
            throw new RuntimeException(PSYCHOLOGIST_NULL_ID);
        }

        Optional<Psychologist> oPsychologist = psychologistRepository.findById(psychologistDTO.getId());

        if (oPsychologist.isPresent()) {
            Psychologist existingPsychologist = oPsychologist.get();

            if (psychologistDTO.getEmail() != null && !psychologistDTO.getEmail().isEmpty()) {
                existingPsychologist.setEmail(psychologistDTO.getEmail());
            }

            if (psychologistDTO.getPhone() != null && !psychologistDTO.getPhone().isEmpty()) {
                existingPsychologist.setPhone(psychologistDTO.getPhone());
            }

            if (psychologistDTO.getStatus() != null) {
                existingPsychologist.setStatus(psychologistDTO.getStatus());
            }

            if (psychologistDTO.getLicenseNumber() != null && !psychologistDTO.getLicenseNumber().isEmpty()) {
                existingPsychologist.setLicenseNumber(psychologistDTO.getLicenseNumber());
            }

            if (psychologistDTO.getSpecializationArea() != null && !psychologistDTO.getSpecializationArea().isEmpty()) {
                existingPsychologist.setSpecializationArea(psychologistDTO.getSpecializationArea());
            }

            if (psychologistDTO.getBiography() != null && !psychologistDTO.getBiography().isEmpty()) {
                existingPsychologist.setBiography(psychologistDTO.getBiography());
            }

            List<Session> mergedSessions = null;
            if (psychologistDTO.getSessions() != null && !psychologistDTO.getSessions().isEmpty()) {
                mergedSessions = EntityUtils.mergeAndUpdateSessions(
                        existingPsychologist.getSessions(),
                        psychologistDTO.getSessions(),
                        SessionDTO::getId,
                        Session::getId
                );
                existingPsychologist.setSessions(mergedSessions);
            }

            if (psychologistDTO.getUpdatedBy() != null && !psychologistDTO.getUpdatedBy().isEmpty()) {
                existingPsychologist.setUpdatedBy(psychologistDTO.getUpdatedBy());
            }

            existingPsychologist.setUpdatedAt(LocalDateTime.now());

            try {
                Psychologist updatedPsychologist = psychologistRepository.save(existingPsychologist);
                return mapper.map(updatedPsychologist, PsychologistDTO.class);
            } catch (Exception e) {
                logger.error(PSYCHOLOGIST_UPDATE_ERROR, e);
                throw new RuntimeException(PSYCHOLOGIST_UPDATE_ERROR);
            }
        } else {
            throw new RuntimeException(PSYCHOLOGIST_ID_NOT_FOUND);
        }
    }

    @Override
    public PsychologistGetDTO findById(Integer id) {
        Optional<Psychologist> oPsychologist = psychologistRepository.findById(id);
        return oPsychologist.map(psychologist -> mapper.map(psychologist, PsychologistGetDTO.class)).orElse(null);
    }

    @Override
    public List<PsychologistGetDTO> listAll() {
        return psychologistRepository.findAllActivePsychologists().stream()
                .map(psychologist -> mapper.map(psychologist, PsychologistGetDTO.class))
                .peek(psychologistDTO -> {
                    Integer id = psychologistDTO.getId();
                    List<SessionInPsychologistListDTO> sessions = sessionService.findSessionsByPsychologistId(id);
                    psychologistDTO.setSessions(sessions);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        softDeletePsychologistById(id);
    }

    public void softDeletePsychologistById(Integer id) {
        Optional<Psychologist> optionalPsychologist = psychologistRepository.findById(id);

        if(optionalPsychologist.isPresent()) {
            Psychologist psychologist = optionalPsychologist.get();
            psychologist.setIsDeleted(true);
            psychologistRepository.save(psychologist);
        } else {
            throw new ResourceNotFoundException("Psychologist with ID " + id + " not found");
        }
    }
}
