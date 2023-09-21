package com.moredevs.psychclinic.models.dtos;

import com.moredevs.psychclinic.models.enums.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PsychologistDTO {
    private Integer id;
    private String email;
    private String password;
    private String phone;
    private List<SessionDTO> sessions;
    private String licenseNumber;
    private String specializationArea;
    private String biography;
    private Status status;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
