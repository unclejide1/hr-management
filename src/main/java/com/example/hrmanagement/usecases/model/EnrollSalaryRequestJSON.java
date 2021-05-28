package com.example.hrmanagement.usecases.model;


import com.example.hrmanagement.usecases.dto.request.EnrollSalaryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class EnrollSalaryRequestJSON {

    @ApiModelProperty(notes = "valid user ID", required = true)
    @NotNull(message = "must pass an id")
    private Long id;


    @ApiModelProperty(notes = "Salary Amount", required = true)
    @NotNull(message = "must pass salary Amount")
    private BigDecimal amount;


    public EnrollSalaryRequest toRequest(){
        return EnrollSalaryRequest.builder()
                .userId(id)
                .amount(amount)
                .build();
    }
}
