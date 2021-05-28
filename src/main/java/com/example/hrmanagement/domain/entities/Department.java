package com.example.hrmanagement.domain.entities;

import com.example.hrmanagement.domain.enums.DeptConst;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department extends AbstractBaseEntity<Long>{

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private DeptConst dept;
}
