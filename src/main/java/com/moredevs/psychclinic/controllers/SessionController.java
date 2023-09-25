package com.moredevs.psychclinic.controllers;

import com.moredevs.psychclinic.models.dtos.SessionCreateDTO;
import com.moredevs.psychclinic.models.dtos.SessionDTO;
import org.springframework.http.ResponseEntity;

public interface SessionController extends Controller<SessionDTO> {
    ResponseEntity<SessionDTO> create(SessionCreateDTO sessionCreateDTO);
}
