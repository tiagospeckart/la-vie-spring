package com.moredevs.psychclinic.models.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
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
public class Admin extends Person {
    @NotBlank
    @Size(min = 8, max = 64)
    private String password;
}
