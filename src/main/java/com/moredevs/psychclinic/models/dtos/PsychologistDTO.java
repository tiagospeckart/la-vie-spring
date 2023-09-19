package com.moredevs.psychclinic.models.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PsychologistDTO extends PersonDTO {
    private List<SessionDTO> sessions;
    private String password;
    private String licenseNumber;
    private String specializationArea;
    private String biography;
}
