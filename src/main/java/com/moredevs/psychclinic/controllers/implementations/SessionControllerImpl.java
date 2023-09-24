package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.SessionController;
import com.moredevs.psychclinic.exceptions.NotFoundException;
import com.moredevs.psychclinic.models.dtos.SessionDTO;
import com.moredevs.psychclinic.service.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class SessionControllerImpl implements SessionController {

    @Override
    public ResponseEntity<SessionDTO> findById(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<SessionDTO> create(SessionDTO sessionDTO) {
        return null;
    }

    @Override
    public ResponseEntity<SessionDTO> updateById(Integer id, SessionDTO sessionDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<SessionDTO>> listAll() {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteById(Integer id) {
        return null;
    }
}
