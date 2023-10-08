package com.moredevs.psychclinic.models.dtos;

import com.moredevs.psychclinic.models.enums.SessionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class SessionCreateDTO {

    @Schema(description = "ID of the associated Psychologist", example = "1")
    @NotNull(message = "Psychologist ID must not be null")
    private Integer psychologistId;

    @Schema(description = "ID of the associated Client", example = "2")
    @NotNull(message = "Client ID must not be null")
    private Integer clientId;

    @Schema(description = "Date and time of the session", example = "2023-10-07T15:00")
    @NotNull(message = "Date and Time must not be null")
    private LocalDateTime dateAndTime;

    @Schema(description = "Notes related to the session", example = "Initial assessment was performed.")
    private String sessionNotes;

    @Schema(description = "Status of the session", example = "COMPLETED")
    @NotNull(message = "Session Status must not be null")
    private SessionStatus sessionStatus;
}
