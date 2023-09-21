package com.moredevs.psychclinic.models.entities;

import com.moredevs.psychclinic.models.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

import static com.moredevs.psychclinic.utils.constants.Constants.PASSWORD_MAX_SIZE;
import static com.moredevs.psychclinic.utils.constants.Constants.PASSWORD_MIN_SIZE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString(callSuper = true)
public class Psychologist {

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
    @Column(columnDefinition = "status_enum")
    private Status status;

    @OneToMany(mappedBy = "psychologist")
    private List<Session> sessions;

    @NotBlank
    @Size(min = PASSWORD_MIN_SIZE, max = PASSWORD_MAX_SIZE)
    private String password;

    @Column(unique = true)
    @NotNull
    private String licenseNumber;

    @Size(max = 100)
    private String specializationArea;

    @Size(max = 1000)
    private String biography;

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
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = this.updatedBy != null ? this.updatedBy : "system";
    }
}
