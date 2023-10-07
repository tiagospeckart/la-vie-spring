package com.moredevs.psychclinic.models.dtos;
import io.swagger.v3.oas.annotations.media.Schema;

import com.moredevs.psychclinic.models.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PsychologistCreateDTO {
    @Schema(description = "Full Name of the Psychologist", example = "Jane Doe")
    @NotNull(message = "Name must not be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @Schema(description = "Email address", example = "jane.doe@example.com")
    @NotNull(message = "Email must not be null")
    @Email(message = "Invalid email format")
    @Size(max = 40, message = "Email can have at most 40 characters")
    private String email;

    @Schema(description = "Password", example = "StrongP@ss123")
    @NotNull(message = "Password must not be null")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    private String password;

    @Schema(description = "Phone number", example = "+1-555-555-5555")
    @Size(max = 30, message = "Phone number can have at most 30 characters")
    private String phone;

    @Schema(description = "License Number", example = "PSY123456")
    @NotNull(message = "License number must not be null")
    @Size(min = 1, max = 30, message = "License number must be between 1 and 30 characters")
    private String licenseNumber;

    @Schema(description = "Specialization Area", example = "Clinical")
    @Size(max = 100, message = "Specialization area can have at most 100 characters")
    private String specializationArea;

    @Schema(description = "Biography", example = "Dr. Jane Doe has over 10 years of experience in clinical psychology.")
    @Size(max = 1000, message = "Biography can have at most 1000 characters")
    private String biography;
}