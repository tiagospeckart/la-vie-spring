package com.moredevs.psychclinic.repository;

import com.moredevs.psychclinic.models.entities.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsychologistRepository extends JpaRepository<Psychologist, Integer> {
}
