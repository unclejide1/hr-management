package com.example.hrmanagement.infrastructure.persistence.daoImpl;



import com.example.hrmanagement.domain.dao.BonusDao;
import com.example.hrmanagement.domain.entities.Bonus;
import com.example.hrmanagement.domain.entities.User;
import com.example.hrmanagement.domain.enums.RecordStatusConstant;
import com.example.hrmanagement.infrastructure.exceptions.CustomException;
import com.example.hrmanagement.infrastructure.persistence.repository.BonusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;


@Named
public class BonusDaoImpl extends CrudDaoImpl<Bonus, Long> implements BonusDao {

    private final BonusRepository repository;


    @Autowired
    public BonusDaoImpl(BonusRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Bonus getBonusByUser(User user) {
        return findBonusByUser(user).orElseThrow(() -> new CustomException("could not get salary details for user:" + user.getUsername(), HttpStatus.NOT_FOUND));

    }

    @Override
    public Optional<Bonus> findBonusByUser(User user) {
        return repository.findFirstByUserAndRecordStatus(user, RecordStatusConstant.ACTIVE);
    }

    @Override
    public List<Bonus> getAllBonusByUser(User user) {
        return repository.getAllByUserAndRecordStatus(user, RecordStatusConstant.ACTIVE);
    }

}
