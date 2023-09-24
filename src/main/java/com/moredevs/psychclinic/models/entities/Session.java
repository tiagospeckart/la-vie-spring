package com.moredevs.psychclinic.models.entities;

import com.moredevs.psychclinic.models.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "psychologist_id", updatable = false)
    private Psychologist psychologist;

    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false)
    private Client client;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime dateAndTime;

    @Column(columnDefinition = "text")
    private String sessionNotes;

    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;

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
