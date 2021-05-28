package com.example.hrmanagement.domain.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SalaryHistory extends AbstractBaseEntity<Long>{


    @ManyToOne(fetch = FetchType.LAZY)
    private Salary salary;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
