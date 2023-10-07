package com.moredevs.psychclinic.controllers;

import com.moredevs.psychclinic.models.dtos.PsychologistCreateDTO;
import com.moredevs.psychclinic.models.dtos.PsychologistDTO;
import com.moredevs.psychclinic.models.dtos.PsychologistGetDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PsychologistController {
    ResponseEntity<PsychologistDTO> create(PsychologistCreateDTO psychologistCreateDTO);
    ResponseEntity<PsychologistDTO> updateById(Integer id, PsychologistDTO updatingPsychologist);
    ResponseEntity<PsychologistGetDTO> findById(Integer id);
    ResponseEntity<List<PsychologistGetDTO>> listAll();
    ResponseEntity<Void> deleteById(Integer id);
}
