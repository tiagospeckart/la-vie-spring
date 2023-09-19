package com.moredevs.psychclinic.models.dtos;

import com.moredevs.psychclinic.models.enums.Status;

import java.time.LocalDateTime;

public abstract class PersonDTO {
    private Integer id;
    private String email;
    private String phone;
    private Status status;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
