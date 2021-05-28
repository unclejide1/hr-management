package com.example.hrmanagement.usecases.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBonusRequest {
    private String username;
    private int electionYear;

    private String candidateId;
}
