package com.example.hrmanagement.usecases.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollSalaryRequest {

    private Long userId;

    private BigDecimal amount;

}
