package com.moredevs.psychclinic.repositories;

import com.moredevs.psychclinic.models.entities.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PsychologistRepository extends JpaRepository<Psychologist, Integer> {
    @Query("SELECT p FROM Psychologist p WHERE p.isDeleted = false")
    List<Psychologist> findAllActivePsychologists();

}
