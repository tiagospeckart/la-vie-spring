package com.moredevs.psychclinic.models.entities;

import com.moredevs.psychclinic.models.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class Client {

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

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @Size(max = 150)
    private String address;

    private String observations;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Session> sessions;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @LastModifiedBy
    private String updatedBy;

    // Lifecycle Callbacks
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.createdBy = this.createdBy != null ? this.createdBy : "system";
        this.updatedBy = this.updatedBy != null ? this.updatedBy : "system";
        if (status == null) {
            status = Status.ACTIVE;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = this.updatedBy != null ? this.updatedBy : "system";
    }

}
