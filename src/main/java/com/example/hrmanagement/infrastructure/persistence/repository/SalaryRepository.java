package com.example.hrmanagement.infrastructure.persistence.repository;

import com.example.hrmanagement.domain.entities.Salary;

import com.example.hrmanagement.domain.entities.User;
import com.example.hrmanagement.domain.enums.RecordStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    Optional<Salary> findFirstByUserAndRecordStatus (User user, RecordStatusConstant statusConstant);

}
