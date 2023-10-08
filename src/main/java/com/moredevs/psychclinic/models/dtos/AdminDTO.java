package com.moredevs.psychclinic.models.dtos;

import com.moredevs.psychclinic.models.enums.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminDTO extends BaseAuditDTO{
    private Integer id;
    private String email;
    private String password;
    private String phone;
    private Status status;
}
