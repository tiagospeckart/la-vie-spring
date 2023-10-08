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
public class SessionInClientListDTO {
    private String psychologistName;
    private LocalDateTime dateAndTime;
    private String sessionNotes;
    private SessionStatus sessionStatus;
}
