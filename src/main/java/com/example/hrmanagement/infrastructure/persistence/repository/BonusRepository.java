package com.example.hrmanagement.infrastructure.persistence.repository;

import com.example.hrmanagement.domain.entities.Bonus;
import com.example.hrmanagement.domain.entities.User;
import com.example.hrmanagement.domain.entities.Vacation;
import com.example.hrmanagement.domain.enums.RecordStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
    Optional<Bonus> findFirstByUserAndRecordStatus (User user, RecordStatusConstant statusConstant);
    List<Bonus> getAllByUserAndRecordStatus(User user, RecordStatusConstant statusConstant);
}
