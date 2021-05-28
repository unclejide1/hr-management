package com.example.hrmanagement.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Bonus extends AbstractBaseEntity<Long>{

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
