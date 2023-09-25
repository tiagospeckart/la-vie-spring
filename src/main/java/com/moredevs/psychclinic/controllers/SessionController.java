package com.moredevs.psychclinic.controllers;

import com.moredevs.psychclinic.models.dtos.SessionCreateDTO;
import com.moredevs.psychclinic.models.dtos.SessionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionController extends Controller<SessionDTO> {
    ResponseEntity<SessionDTO> create(SessionCreateDTO sessionCreateDTO);
    ResponseEntity<Boolean> completeSessionById(@PathVariable Integer sessionId);
    ResponseEntity<SessionDTO> rescheduleSessionById(Integer sessionId,
                                                     LocalDateTime newDateTime);
    ResponseEntity<Boolean> cancelSessionById(Integer sessionId);
    ResponseEntity<List<SessionDTO>> listPsychologistSessionsById(Integer psychologistId);
    ResponseEntity<List<SessionDTO>> listClientSessionsById(Integer clientId);
    ResponseEntity<List<SessionDTO>> listClientPsychologistSessionsById(Integer clientId,
                                                                        Integer psychologistId);
}
