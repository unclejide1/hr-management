package com.example.hrmanagement.infrastructure.persistence.daoImpl;

import com.example.hrmanagement.domain.dao.VacationDao;
import com.example.hrmanagement.domain.entities.User;
import com.example.hrmanagement.domain.entities.Vacation;

import com.example.hrmanagement.domain.enums.RecordStatusConstant;
import com.example.hrmanagement.infrastructure.persistence.repository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;


@Named
public class VacationDaoImpl extends CrudDaoImpl<Vacation, Long> implements VacationDao {

    private final VacationRepository repository;


    @Autowired
    public VacationDaoImpl(VacationRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Vacation> getAllVacationsByUser(User user) {
        return repository.getAllByUserAndRecordStatus(user, RecordStatusConstant.ACTIVE);
    }
}
