package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.PsychologistController;
import com.moredevs.psychclinic.exceptions.NotFoundException;
import com.moredevs.psychclinic.models.dtos.PsychologistDTO;
import com.moredevs.psychclinic.service.PsychologistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PsychologistControllerImpl implements PsychologistController {

    @Autowired
    private PsychologistService psychologistService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PsychologistDTO findById(int id) {
        return null;
    }

    @Override
    public List<PsychologistDTO> listAll() {
        return null;
    }

    @Override
    public void save(PsychologistDTO entity) throws NotFoundException {

    }

    @Override
    public void delete(int id) {

    }
}
