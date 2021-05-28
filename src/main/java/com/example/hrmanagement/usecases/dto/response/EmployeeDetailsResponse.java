package com.example.hrmanagement.usecases.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDetailsResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String gender;
    private String dept;
    private BigDecimal salaryAmount;
    private BigDecimal bonusAmount;
    private Integer vacationDays;
    private Integer vacationDaysLeft;
}
