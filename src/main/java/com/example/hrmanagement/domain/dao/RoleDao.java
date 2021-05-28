package com.example.hrmanagement.domain.dao;



import com.example.hrmanagement.domain.entities.Role;
import com.example.hrmanagement.domain.enums.ERole;

import java.util.Optional;

public interface RoleDao extends CrudDao<Role, Long> {
    Optional<Role> findByRole(ERole role);
}
