package com.moredevs.psychclinic.repositories;

import com.moredevs.psychclinic.models.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Integer> {
}
