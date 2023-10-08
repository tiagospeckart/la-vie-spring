package com.moredevs.psychclinic.models.dtos;

import com.moredevs.psychclinic.models.enums.Status;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ClientGetDTO extends BaseAuditDTO {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private LocalDate dateOfBirth;
    private List<SessionInClientListDTO> sessions;
    private String observations;
    private Status status;
    private Boolean isDeleted;
}
