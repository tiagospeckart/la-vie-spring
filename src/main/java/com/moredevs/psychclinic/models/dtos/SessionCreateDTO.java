package com.moredevs.psychclinic.models.dtos;

import com.moredevs.psychclinic.models.enums.SessionStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class SessionCreateDTO {
    private Integer psychologistId;
    private Integer clientId;
    private LocalDateTime dateAndTime;
    private String sessionNotes;
    private SessionStatus sessionStatus;
}
