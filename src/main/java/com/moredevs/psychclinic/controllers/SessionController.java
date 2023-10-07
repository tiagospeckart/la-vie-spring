package com.moredevs.psychclinic.controllers;

import com.moredevs.psychclinic.models.dtos.SessionCreateDTO;
import com.moredevs.psychclinic.models.dtos.SessionDTO;
import com.moredevs.psychclinic.models.dtos.SessionGetDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionController {
    ResponseEntity<SessionDTO> create(SessionCreateDTO sessionCreateDTO);
    ResponseEntity<SessionGetDTO> findById(Integer id);
    ResponseEntity<List<SessionGetDTO>> listAll();
    ResponseEntity<SessionDTO> updateById(Integer id, SessionDTO updatingSession);
    ResponseEntity<Void> deleteById(Integer id);
    ResponseEntity<Boolean> completeSessionById(@PathVariable Integer sessionId);
    ResponseEntity<SessionDTO> rescheduleSessionById(Integer sessionId,
                                                     LocalDateTime newDateTime);
    ResponseEntity<Boolean> cancelSessionById(Integer sessionId);
    ResponseEntity<List<SessionGetDTO>> listPsychologistSessionsById(Integer psychologistId);
    ResponseEntity<List<SessionGetDTO>> listClientSessionsById(Integer clientId);
    ResponseEntity<List<SessionGetDTO>> listClientPsychologistSessionsById(Integer clientId,
                                                                        Integer psychologistId);
}
