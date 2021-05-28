package com.example.hrmanagement.infrastructure.persistence.daoImpl;



import com.example.hrmanagement.domain.dao.SalaryDao;
import com.example.hrmanagement.domain.entities.Salary;
import com.example.hrmanagement.domain.entities.User;
import com.example.hrmanagement.domain.enums.RecordStatusConstant;
import com.example.hrmanagement.infrastructure.exceptions.CustomException;
import com.example.hrmanagement.infrastructure.persistence.repository.SalaryRepository;
import org.springframework.http.HttpStatus;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named
public class SalaryDaoImpl extends CrudDaoImpl<Salary, Long> implements SalaryDao {
    private final SalaryRepository repository;


    public SalaryDaoImpl(SalaryRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Salary getSalaryByUser(User user) {
        return findSalaryUser(user).orElseThrow(() -> new CustomException("could not get salary details for user:" + user.getUsername(), HttpStatus.NOT_FOUND));
    }

    @Override
    public Optional<Salary> findSalaryUser(User user) {
        return repository.findFirstByUserAndRecordStatus(user, RecordStatusConstant.ACTIVE);
    }


}
