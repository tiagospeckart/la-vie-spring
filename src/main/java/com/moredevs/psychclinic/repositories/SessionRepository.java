package com.moredevs.psychclinic.repositories;

import com.moredevs.psychclinic.models.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    List<Session> findByClientId(Integer clientId);
    List<Session> findByPsychologistId(Integer psychologistId);
    List<Session> findByClientIdAndPsychologistId(Integer clientId, Integer psychologistId);
}
