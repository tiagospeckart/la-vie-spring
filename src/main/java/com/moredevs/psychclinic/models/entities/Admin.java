package com.moredevs.psychclinic.models.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

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
public class Admin extends Person {
    @NotBlank
    @Size(min = PASSWORD_MIN_SIZE, max = PASSWORD_MAX_SIZE)
    private String password;
}
