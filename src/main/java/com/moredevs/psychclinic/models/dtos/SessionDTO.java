package com.moredevs.psychclinic.models.dtos;

import com.moredevs.psychclinic.models.enums.SessionStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SessionDTO extends BaseAuditDTO {
    private Integer id;
    private PsychologistDTO psychologistDTO;
    private ClientDTO clientDTO;
    private LocalDateTime dateAndTime;
    private String sessionNotes;
    private SessionStatus sessionStatus;
}