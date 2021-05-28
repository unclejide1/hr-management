package com.example.hrmanagement.domain.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Salary extends AbstractBaseEntity<Long>{

    private BigDecimal amount;

    @OneToOne (fetch = FetchType.LAZY)
    private User user;
}
