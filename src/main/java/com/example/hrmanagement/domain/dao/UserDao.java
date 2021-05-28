package com.example.hrmanagement.domain.dao;


import com.example.hrmanagement.domain.entities.Department;
import com.example.hrmanagement.domain.entities.User;
import com.example.hrmanagement.domain.enums.DeptConst;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<User, Long> {
    User getUserByUsername(String username);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByPhoneNumber(String phoneNumber);
    Boolean existsByUsername(String username);
    List<User> getAllUsersByDept(Department department);
    List<User> getAllUsers();
}
