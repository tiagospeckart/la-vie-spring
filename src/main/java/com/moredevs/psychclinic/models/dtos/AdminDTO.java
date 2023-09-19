package com.moredevs.psychclinic.models.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminDTO extends PersonDTO {
    private String password;
}
