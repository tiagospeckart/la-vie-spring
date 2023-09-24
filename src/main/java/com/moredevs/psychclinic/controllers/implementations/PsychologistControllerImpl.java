package com.moredevs.psychclinic.controllers.implementations;

import com.moredevs.psychclinic.controllers.PsychologistController;
import com.moredevs.psychclinic.exceptions.NotFoundException;
import com.moredevs.psychclinic.models.dtos.PsychologistDTO;
import com.moredevs.psychclinic.service.PsychologistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class PsychologistControllerImpl implements PsychologistController {

    @Override
    public ResponseEntity<PsychologistDTO> findById(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<PsychologistDTO> create(PsychologistDTO psychologistDTO) {
        return null;
    }

    @Override
    public ResponseEntity<PsychologistDTO> updateById(Integer id, PsychologistDTO psychologistDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<PsychologistDTO>> listAll() {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteById(Integer id) {
        return null;
    }
}
