package com.moredevs.psychclinic.repository;

import com.moredevs.psychclinic.models.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
