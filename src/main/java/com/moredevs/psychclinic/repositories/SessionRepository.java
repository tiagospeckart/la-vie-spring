package com.moredevs.psychclinic.repositories;

import com.moredevs.psychclinic.models.dtos.SessionInPsychologistListDTO;
import com.moredevs.psychclinic.models.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long>, JpaSpecificationExecutor<Session> {
    List<Session> findAllByClientId(Integer clientId);
    List<Session> findAllByPsychologistId(Integer psychologistId);
    List<Session> findAllByClientIdAndPsychologistId(Integer clientId, Integer psychologistId);
}
