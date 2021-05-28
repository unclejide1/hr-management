package com.example.hrmanagement.infrastructure.persistence.daoImpl;

import com.example.hrmanagement.domain.dao.SalaryHistoryDao;
import com.example.hrmanagement.domain.entities.SalaryHistory;
import com.example.hrmanagement.infrastructure.persistence.repository.SalaryHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.Optional;

@Named
public class SalaryHistoryDaoImpl extends CrudDaoImpl<SalaryHistory, Long> implements SalaryHistoryDao {

    private final SalaryHistoryRepository repository;


    @Autowired
    public SalaryHistoryDaoImpl(SalaryHistoryRepository repository) {
        super(repository);
        this.repository = repository;
    }


}
