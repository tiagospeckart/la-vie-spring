package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.SessionController;
import com.moredevs.psychclinic.exceptions.NotFoundException;
import com.moredevs.psychclinic.models.dtos.SessionDTO;
import com.moredevs.psychclinic.service.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SessionControllerImpl implements SessionController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SessionDTO findById(int id) {
        return null;
    }

    @Override
    public List<SessionDTO> listAll() {
        return null;
    }

    @Override
    public void save(SessionDTO entity) throws NotFoundException {

    }

    @Override
    public void delete(int id) {

    }
}
