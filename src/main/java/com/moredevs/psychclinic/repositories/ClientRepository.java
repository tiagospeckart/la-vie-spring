package com.moredevs.psychclinic.repositories;

import com.moredevs.psychclinic.models.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
