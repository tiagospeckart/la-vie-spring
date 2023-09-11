package com.moredevs.psychclinic.models.entities;

import com.moredevs.psychclinic.models.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Email
    @NotNull
    @Column(length = 40, nullable = false)
    private String email;
    @Column(length = 30)
    private String phone;
    @Enumerated(EnumType.STRING)
    private Status status;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @LastModifiedBy
    @Column(nullable = false)
    private String updatedBy;

}
