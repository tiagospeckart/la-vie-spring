package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.SessionController;
import com.moredevs.psychclinic.models.dtos.SessionCreateDTO;
import com.moredevs.psychclinic.models.dtos.SessionDTO;
import com.moredevs.psychclinic.models.dtos.SessionGetDTO;
import com.moredevs.psychclinic.services.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Session Management", description = "Endpoints for managing sessions")
public class SessionControllerImpl implements SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/{id}")
    @Operation(summary = "Find Session by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Session found"),
                    @ApiResponse(responseCode = "404", description = "Session not found")
            })
    @Override
    public ResponseEntity<SessionGetDTO> findById(@PathVariable Integer id) {
        SessionGetDTO sessionGetDTO = sessionService.findById(id);
        if (sessionGetDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sessionGetDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new Session",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Session created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @Override
    public ResponseEntity<SessionDTO> create(@RequestBody SessionCreateDTO sessionCreateDTO) {
        SessionDTO createdSession = sessionService.create(sessionCreateDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSession.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdSession);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Session by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Session updated successfully"),
            @ApiResponse(responseCode = "404", description = "Session not found")
    })
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
    @Operation(summary = "List all Sessions", responses = {
            @ApiResponse(responseCode = "200", description = "Sessions listed")
    })
    @Override
    public ResponseEntity<List<SessionGetDTO>> listAll() {
        List<SessionGetDTO> allSessions = sessionService.listAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(allSessions.size()));
        return ResponseEntity.ok().headers(headers).body(allSessions);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Session by ID", responses = {
            @ApiResponse(responseCode = "204", description = "Session deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Session not found")
    })
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        sessionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{id}/complete")
    @Operation(summary = "Mark Session as complete",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Session marked as complete"),
                    @ApiResponse(responseCode = "404", description = "Session not found")
            })
    public ResponseEntity<Boolean> completeSessionById(@PathVariable Integer id) {
        boolean isCompleted = sessionService.completeSessionById(id);
        return ResponseEntity.ok(isCompleted);
    }

    @Override
    @PutMapping("/{id}/reschedule")
    @Operation(summary = "Reschedule Session",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Session rescheduled"),
                    @ApiResponse(responseCode = "404", description = "Session not found")
            })
    public ResponseEntity<SessionDTO> rescheduleSessionById(@PathVariable Integer id,
                                                            @RequestBody LocalDateTime newDateTime) {
        SessionDTO rescheduledSession = sessionService.rescheduleSessionById(id, newDateTime);
        if (rescheduledSession == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rescheduledSession);
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel Session", responses = {
            @ApiResponse(responseCode = "200", description = "Session canceled"),
            @ApiResponse(responseCode = "404", description = "Session not found")
    })
    @Override
    public ResponseEntity<Boolean> cancelSessionById(@PathVariable Integer id) {
        boolean isCanceled = sessionService.cancelSessionById(id);
        return ResponseEntity.ok(isCanceled);
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "List Sessions by Client ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sessions listed")
    })
    @Override
    public ResponseEntity<List<SessionGetDTO>> listClientSessionsById(@PathVariable Integer clientId) {
        List<SessionGetDTO> clientSessions = sessionService.listClientSessionsById(clientId);
        return ResponseEntity.ok(clientSessions);
    }

    @GetMapping("/psychologist/{psychologistId}")
    @Operation(summary = "List Sessions by Psychologist ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sessions listed")
    })
    @Override
    public ResponseEntity<List<SessionGetDTO>> listPsychologistSessionsById(@PathVariable Integer psychologistId) {
        List<SessionGetDTO> psychologistSessions = sessionService.listPsychologistSessionsById(psychologistId);
        return ResponseEntity.ok(psychologistSessions);
    }

    @Override
    @GetMapping("/client/{clientId}/psychologist/{psychologistId}")
    @Operation(summary = "List Sessions by Client and Psychologist IDs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sessions listed")
            })
    public ResponseEntity<List<SessionGetDTO>> listClientPsychologistSessionsById(@PathVariable Integer clientId,
                                                                                  @PathVariable Integer psychologistId) {
        List<SessionGetDTO> sessions = sessionService.listClientPsychologistSessionsById(clientId, psychologistId);
        return ResponseEntity.ok(sessions);
    }
}
