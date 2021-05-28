package com.example.hrmanagement.domain.dao;

import com.example.hrmanagement.domain.entities.Bonus;
import com.example.hrmanagement.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface BonusDao extends CrudDao<Bonus, Long>{
    Bonus getBonusByUser(User user);
    Optional<Bonus> findBonusByUser(User user);
    List<Bonus> getAllBonusByUser(User user);
}
