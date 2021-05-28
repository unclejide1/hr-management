package com.example.hrmanagement.infrastructure.persistence.daoImpl;

import com.example.hrmanagement.domain.dao.DepartmentDao;
import com.example.hrmanagement.domain.entities.Department;
import com.example.hrmanagement.domain.enums.DeptConst;
import com.example.hrmanagement.domain.enums.RecordStatusConstant;
import com.example.hrmanagement.infrastructure.persistence.repository.DepartmentRepository;


import javax.inject.Named;
import java.util.Optional;

@Named
public class DepartmentDaoImpl extends CrudDaoImpl<Department, Long> implements DepartmentDao {

    private final DepartmentRepository repository;


    public DepartmentDaoImpl(DepartmentRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Optional<Department> findByDept(DeptConst deptConst) {
        return repository.findFirstByDeptAndRecordStatus(deptConst, RecordStatusConstant.ACTIVE);
    }

    @Override
    public Department getDepartmentByDeptConst(DeptConst deptConst) {
       return findByDept(deptConst).orElseThrow(() -> new RuntimeException("Not Found. Department: " + deptConst.name()));
    }
}
