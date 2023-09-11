package com.moredevs.psychclinic.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@ToString(callSuper = true)
public class Psychologist extends Person {
    @NotBlank
    @Size(min = 8, max = 64)
    private String password;

    @Column(unique = true)
    @NotNull
    private String licenseNumber;

    @Size(max = 100)
    private String specializationArea;

    @Size(max = 1000)
    private String biography;
}
