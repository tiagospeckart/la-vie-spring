package com.moredevs.psychclinic.services;

import com.moredevs.psychclinic.models.dtos.PsychologistCreateDTO;
import com.moredevs.psychclinic.models.dtos.PsychologistDTO;
import com.moredevs.psychclinic.models.dtos.PsychologistGetDTO;

import java.util.List;

public interface PsychologistService {
    PsychologistDTO create(PsychologistCreateDTO psychologistDTO);
    PsychologistDTO save(PsychologistCreateDTO psychologistDTO);
    PsychologistGetDTO findById(Integer id);
    List<PsychologistGetDTO> listAll();
    PsychologistDTO update(PsychologistDTO updatingPsychologist);
    void deleteById(Integer id);
}
