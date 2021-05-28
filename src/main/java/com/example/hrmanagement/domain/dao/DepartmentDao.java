package com.example.hrmanagement.domain.dao;

import com.example.hrmanagement.domain.entities.Department;
import com.example.hrmanagement.domain.entities.User;
import com.example.hrmanagement.domain.enums.DeptConst;

import java.util.Optional;


public interface DepartmentDao extends CrudDao<Department, Long>{
    Optional<Department> findByDept(DeptConst deptConst);
    Department getDepartmentByDeptConst(DeptConst deptConst);
}
