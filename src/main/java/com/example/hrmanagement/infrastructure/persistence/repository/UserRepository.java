package com.example.hrmanagement.infrastructure.persistence.repository;


import com.example.hrmanagement.domain.entities.Department;
import com.example.hrmanagement.domain.entities.User;
import com.example.hrmanagement.domain.enums.DeptConst;
import com.example.hrmanagement.domain.enums.RecordStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByUsernameAndRecordStatus(String username, RecordStatusConstant statusConstant);
    Optional<User> findFirstByPhoneNumber(String phoneNumber);
    Boolean existsByUsernameAndRecordStatus(String username, RecordStatusConstant statusConstant);
    List<User> getAllByDepartmentsAndRecordStatus(Department departments, RecordStatusConstant recordStatus);
    List<User> getAllByRecordStatus(RecordStatusConstant recordStatusConstant);
}
