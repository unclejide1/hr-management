package com.example.hrmanagement.domain.dao;

import com.example.hrmanagement.domain.entities.User;
import com.example.hrmanagement.domain.entities.Vacation;

import java.util.List;

public interface VacationDao extends CrudDao<Vacation, Long>{
    List<Vacation> getAllVacationsByUser(User user);
}
