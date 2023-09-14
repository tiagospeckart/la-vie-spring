package com.moredevs.psychclinic.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

import static com.moredevs.psychclinic.utils.Constants.PASSWORD_MAX_SIZE;
import static com.moredevs.psychclinic.utils.Constants.PASSWORD_MIN_SIZE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@ToString(callSuper = true)
public class Psychologist extends Person {

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
}
