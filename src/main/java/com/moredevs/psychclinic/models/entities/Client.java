package com.moredevs.psychclinic.models.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@ToString(callSuper = true)
public class Client extends Person {
    @NotNull
    @Past
    private Date dateOfBirth;

    @Size(max = 150)
    private String address;

    private String observations;
}
