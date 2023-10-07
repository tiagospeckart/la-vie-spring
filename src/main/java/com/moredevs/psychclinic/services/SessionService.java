package com.moredevs.psychclinic.services;

import com.moredevs.psychclinic.models.dtos.SessionCreateDTO;
import com.moredevs.psychclinic.models.dtos.SessionDTO;
import com.moredevs.psychclinic.models.dtos.SessionGetDTO;
import com.moredevs.psychclinic.models.dtos.SessionInPsychologistListDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionService {
    SessionDTO create(SessionCreateDTO sessionCreateDTO);
    SessionDTO save(SessionCreateDTO sessionCreateDTO);
    SessionGetDTO findById(Integer id);
    List<SessionGetDTO> listAll();
    List<SessionInPsychologistListDTO> findSessionsByPsychologistId(Integer psychologistId);
    List<SessionGetDTO> listClientSessionsById(Integer clientId);
    List<SessionGetDTO> listPsychologistSessionsById(Integer psychologistId);
    List<SessionGetDTO> listClientPsychologistSessionsById(Integer clientId, Integer psychologistId);
    SessionDTO update(SessionDTO sessionUpdateDTO);
    boolean completeSessionById(Integer sessionId);
    boolean cancelSessionById(Integer sessionId);
    SessionDTO rescheduleSessionById(Integer sessionId, LocalDateTime newDateTime);
    void deleteById(Integer id);
}
