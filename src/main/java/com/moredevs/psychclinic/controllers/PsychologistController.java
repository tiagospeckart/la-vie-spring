package com.moredevs.psychclinic.controllers;

import com.moredevs.psychclinic.models.dtos.PsychologistDTO;
import org.springframework.http.ResponseEntity;

public interface PsychologistController extends Controller<PsychologistDTO> {
    ResponseEntity<PsychologistDTO> create(PsychologistDTO psychologistDTO);
}
