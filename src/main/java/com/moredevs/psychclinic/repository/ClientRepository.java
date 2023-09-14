package com.moredevs.psychclinic.repository;

import com.moredevs.psychclinic.models.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
