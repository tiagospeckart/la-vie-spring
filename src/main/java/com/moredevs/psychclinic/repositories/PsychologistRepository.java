package com.moredevs.psychclinic.repositories;

import com.moredevs.psychclinic.models.entities.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsychologistRepository extends JpaRepository<Psychologist, Integer> {
}
