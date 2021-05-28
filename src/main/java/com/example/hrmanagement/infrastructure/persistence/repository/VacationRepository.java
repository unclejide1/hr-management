package com.example.hrmanagement.infrastructure.persistence.repository;


import com.example.hrmanagement.domain.entities.User;
import com.example.hrmanagement.domain.entities.Vacation;
import com.example.hrmanagement.domain.enums.RecordStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {
    List<Vacation> getAllByUserAndRecordStatus(User user, RecordStatusConstant statusConstant);
}
