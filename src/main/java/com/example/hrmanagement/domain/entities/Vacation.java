package com.example.hrmanagement.domain.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Vacation extends AbstractBaseEntity<Long>{


    private Integer days;

    private Integer daysLeft;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
