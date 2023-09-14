package com.moredevs.psychclinic.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@ToString(callSuper = true)
public class Client extends Person {

    @OneToMany(mappedBy = "client")
    private List<Session> sessions;

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @Size(max = 150)
    private String address;

    private String observations;
}
