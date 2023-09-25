package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.SessionController;
import com.moredevs.psychclinic.models.dtos.SessionCreateDTO;
import com.moredevs.psychclinic.models.dtos.SessionDTO;
import com.moredevs.psychclinic.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/session")
public class SessionControllerImpl implements SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<SessionDTO> findById(@PathVariable Integer id) {
        SessionDTO sessionDTO = sessionService.findById(id);
        if (sessionDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sessionDTO);
    }

    @PostMapping
    public ResponseEntity<SessionDTO> create(@RequestBody SessionCreateDTO sessionCreateDTO) {
        SessionDTO createdSession = sessionService.create(sessionCreateDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSession.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdSession);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<SessionDTO> updateById(@PathVariable Integer id, @RequestBody SessionDTO updatedSession) {
        updatedSession.setId(id);
        SessionDTO savedSession = sessionService.update(updatedSession);
        if (savedSession == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(savedSession);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<SessionDTO>> listAll() {
        List<SessionDTO> allSessions = sessionService.listAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(allSessions.size()));
        return ResponseEntity.ok().headers(headers).body(allSessions);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        sessionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{id}/complete")
    public ResponseEntity<Boolean> completeSessionById(@PathVariable Integer id) {
        boolean isCompleted = sessionService.completeSessionById(id);
        return ResponseEntity.ok(isCompleted);
    }

    @Override
    @PutMapping("/{id}/reschedule")
    public ResponseEntity<SessionDTO> rescheduleSessionById(@PathVariable Integer id,
                                                            @RequestBody LocalDateTime newDateTime) {
        SessionDTO rescheduledSession = sessionService.rescheduleSessionById(id, newDateTime);
        if (rescheduledSession == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rescheduledSession);
    }

    @Override
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Boolean> cancelSessionById(@PathVariable Integer id) {
        boolean isCanceled = sessionService.cancelSessionById(id);
        return ResponseEntity.ok(isCanceled);
    }

    @Override
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<SessionDTO>> listClientSessionsById(@PathVariable Integer clientId) {
        List<SessionDTO> clientSessions = sessionService.listClientSessionsById(clientId);
        return ResponseEntity.ok(clientSessions);
    }

    @Override
    @GetMapping("/psychologist/{psychologistId}")
    public ResponseEntity<List<SessionDTO>> listPsychologistSessionsById(@PathVariable Integer psychologistId) {
        List<SessionDTO> psychologistSessions = sessionService.listPsychologistSessionsById(psychologistId);
        return ResponseEntity.ok(psychologistSessions);
    }

    @Override
    @GetMapping("/client/{clientId}/psychologist/{psychologistId}")
    public ResponseEntity<List<SessionDTO>> listClientPsychologistSessionsById(@PathVariable Integer clientId,
                                                                               @PathVariable Integer psychologistId) {
        List<SessionDTO> sessions = sessionService.listClientPsychologistSessionsById(clientId, psychologistId);
        return ResponseEntity.ok(sessions);
    }
}
