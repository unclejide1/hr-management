package com.example.hrmanagement.infrastructure.persistence.repository;

import com.example.hrmanagement.domain.entities.Role;
import com.example.hrmanagement.domain.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
