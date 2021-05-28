package com.example.hrmanagement.domain.dao;

import com.example.hrmanagement.domain.entities.Salary;
import com.example.hrmanagement.domain.entities.User;

import java.util.Optional;

public interface SalaryDao extends CrudDao<Salary, Long>{
    Salary getSalaryByUser(User user);
    Optional<Salary> findSalaryUser(User user);
}
