package com.moredevs.psychclinic.service;

import com.moredevs.psychclinic.models.dtos.SessionCreateDTO;
import com.moredevs.psychclinic.models.dtos.SessionDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionService extends Service<SessionDTO> {
    SessionDTO create(SessionCreateDTO sessionCreateDTO);
    SessionDTO save(SessionCreateDTO sessionCreateDTO);
    boolean completeSessionById(Integer sessionId);
    SessionDTO rescheduleSessionById(Integer sessionId, LocalDateTime newDateTime);
    boolean cancelSessionById(Integer sessionId);
    List<SessionDTO> listClientSessionsById(Integer clientId);
    List<SessionDTO> listPsychologistSessionsById(Integer psychologistId);
    List<SessionDTO> listClientPsychologistSessionsById(Integer clientId, Integer psychologistId);
}
