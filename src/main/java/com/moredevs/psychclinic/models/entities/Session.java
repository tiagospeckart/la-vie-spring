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

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "psychologist_id")
    private Psychologist psychologist;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime dateAndTime;

    @Column(columnDefinition = "text")
    private String sessionNotes;

    @Column(columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;

    @CreatedDate
    @Column(columnDefinition = "timestamp", updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(columnDefinition = "varchar", updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(columnDefinition = "timestamp", nullable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(columnDefinition = "varchar", nullable = false)
    private String updatedBy;

}
