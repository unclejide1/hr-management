package com.example.hrmanagement.infrastructure.persistence.repository;

import com.example.hrmanagement.domain.entities.SalaryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryHistoryRepository extends JpaRepository<SalaryHistory, Long> {

}
