package com.moredevs.psychclinic.models.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ClientDTO extends PersonDTO{
    private List<SessionDTO> sessions;
    private LocalDate dateOfBirth;
    private String address;
    private String observations;
}
