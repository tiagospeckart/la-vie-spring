package com.moredevs.psychclinic.models.dtos;

import com.moredevs.psychclinic.models.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminCreateDTO {
    @Schema(description = "Email address of the Admin", example = "admin@example.com")
    @NotNull(message = "Email must not be null")
    @Email(message = "Invalid email format")
    @Size(max = 40, message = "Email can have at most 40 characters")
    private String email;

    @Schema(description = "Password for the Admin", example = "SecureP@ss123")
    @NotNull(message = "Password must not be null")
    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")  // Assuming PASSWORD_MIN_SIZE=8, PASSWORD_MAX_SIZE=30
    private String password;

    @Schema(description = "Phone number of the Admin", example = "+1-555-555-5555")
    @Size(max = 30, message = "Phone number can have at most 30 characters")
    private String phone;
}
