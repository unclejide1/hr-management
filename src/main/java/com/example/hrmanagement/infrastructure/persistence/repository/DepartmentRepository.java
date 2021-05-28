package com.example.hrmanagement.infrastructure.persistence.repository;
import com.example.hrmanagement.domain.entities.Department;
import com.example.hrmanagement.domain.entities.Role;
import com.example.hrmanagement.domain.enums.DeptConst;
import com.example.hrmanagement.domain.enums.ERole;
import com.example.hrmanagement.domain.enums.RecordStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findFirstByDeptAndRecordStatus(DeptConst deptConst, RecordStatusConstant recordStatusConstant);
}
