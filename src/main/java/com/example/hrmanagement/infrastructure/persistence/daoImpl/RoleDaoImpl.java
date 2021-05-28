package com.example.hrmanagement.infrastructure.persistence.daoImpl;



import com.example.hrmanagement.domain.dao.RoleDao;
import com.example.hrmanagement.domain.entities.Role;
import com.example.hrmanagement.domain.enums.ERole;
import com.example.hrmanagement.infrastructure.persistence.repository.RoleRepository;

import javax.inject.Named;
import java.util.Optional;

@Named
public class RoleDaoImpl extends CrudDaoImpl<Role, Long> implements RoleDao {

    private final RoleRepository repository;


    public RoleDaoImpl(RoleRepository repository) {
        super(repository);
        this.repository = repository;
    }
    @Override
    public Optional<Role> findByRole(ERole role) {
        return repository.findByName(role);
    }
}
