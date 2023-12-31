package com.moredevs.psychclinic.repositories;

import com.moredevs.psychclinic.models.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    // TODO
    Optional<Admin> findAdminByEmail(String email);

    // TODO
    @Query("SELECT a FROM Admin a where a.status = 'ACTIVE'")
    List<Admin> findAllActiveAdmins();
}
