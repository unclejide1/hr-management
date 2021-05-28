package com.example.hrmanagement.usecases.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ElectionPeriodRequest {
    private int electionYear;

    private LocalDate startDate;

    private LocalDate endDate;
}
