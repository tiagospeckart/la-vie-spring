package com.moredevs.psychclinic.services;

import com.moredevs.psychclinic.models.dtos.PsychologistDTO;

public interface PsychologistService extends Service<PsychologistDTO> {
    public PsychologistDTO create(PsychologistDTO psychologistDTO);
    public PsychologistDTO save(PsychologistDTO psychologistDTO);
}
