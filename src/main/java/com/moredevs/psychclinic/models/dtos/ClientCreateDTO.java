package com.moredevs.psychclinic.models.dtos;

import com.moredevs.psychclinic.models.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ClientCreateDTO {
    @Schema(description = "Full Name of the Client", example = "John Doe")
    @NotNull(message = "Name must not be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @Schema(description = "Address of the Client", example = "123 Main St, Anytown, USA")
    @Size(max = 150, message = "Address can have at most 150 characters")
    private String address;

    @Schema(description = "Phone number of the Client", example = "+1-555-555-5555")
    @Size(max = 30, message = "Phone number can have at most 30 characters")
    private String phone;

    @Schema(description = "Email address of the Client", example = "john.doe@example.com")
    @NotNull(message = "Email must not be null")
    @Email(message = "Invalid email format")
    @Size(max = 40, message = "Email can have at most 40 characters")
    private String email;

    @Schema(description = "Date of Birth of the Client", example = "1990-01-01")
    @NotNull(message = "Date of Birth must not be null")
    @Past(message = "Date of Birth must be a past date")
    private LocalDate dateOfBirth;
}
