package com.example.hrmanagement.infrastructure.persistence.daoImpl;



import com.example.hrmanagement.domain.dao.UserDao;
import com.example.hrmanagement.domain.entities.Department;
import com.example.hrmanagement.domain.entities.User;
import com.example.hrmanagement.domain.enums.RecordStatusConstant;
import com.example.hrmanagement.infrastructure.exceptions.CustomException;
import com.example.hrmanagement.infrastructure.persistence.repository.UserRepository;
import org.springframework.http.HttpStatus;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named
public class UserDaoImpl extends CrudDaoImpl<User, Long> implements UserDao {
    private final UserRepository repository;


    public UserDaoImpl(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public User getUserByUsername(String username) {
        return findUserByUsername(username).orElseThrow(() -> new CustomException("Not Found. User with username: " + username, HttpStatus.NOT_FOUND));
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return repository.findFirstByUsernameAndRecordStatus(username, RecordStatusConstant.ACTIVE);
    }

    @Override
    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        return repository.findFirstByPhoneNumber(phoneNumber);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return repository.existsByUsernameAndRecordStatus(username, RecordStatusConstant.ACTIVE);
    }

    @Override
    public List<User> getAllUsersByDept(Department department) {
        return repository.getAllByDepartmentsAndRecordStatus(department, RecordStatusConstant.ACTIVE);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.getAllByRecordStatus(RecordStatusConstant.ACTIVE);
    }
}
