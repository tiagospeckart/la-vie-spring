package com.moredevs.psychclinic.service;

import com.moredevs.psychclinic.models.dtos.SessionCreateDTO;
import com.moredevs.psychclinic.models.dtos.SessionDTO;
import org.springframework.http.ResponseEntity;

public interface SessionService extends Service<SessionDTO> {
    SessionDTO create(SessionCreateDTO sessionCreateDTO);
    SessionDTO save(SessionCreateDTO sessionCreateDTO);
}
