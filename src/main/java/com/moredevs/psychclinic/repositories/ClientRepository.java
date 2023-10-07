package com.moredevs.psychclinic.repositories;

import com.moredevs.psychclinic.models.entities.Client;
import com.moredevs.psychclinic.models.entities.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query("SELECT c FROM Client c WHERE c.isDeleted = false")
    List<Client> findAllActiveClients();
}
