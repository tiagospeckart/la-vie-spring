package com.moredevs.psychclinic.models.dtos;

import com.moredevs.psychclinic.models.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminGetDTO extends BaseAuditDTO {
    private Integer id;
    private String email;
    private String password;
    private String phone;
    private Status status;
}