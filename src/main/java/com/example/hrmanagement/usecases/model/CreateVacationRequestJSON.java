package com.example.hrmanagement.usecases.model;


import com.example.hrmanagement.usecases.dto.request.CreateVacationRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateVacationRequestJSON {

    @ApiModelProperty(notes = "valid user ID", required = true)
    @NotNull(message = "must pass an id")
    private Long id;

    @ApiModelProperty(notes = "vacation days assigned for a calendar year", required = true)
    @NotNull(message = "must enter vacation days to be assigned for a year")
    private Integer days;

    @ApiModelProperty(notes = "vacation days remaining in a calendar year", required = true)
    @NotNull(message = "vacation days left in a year")
    private Integer daysLeft;

    public CreateVacationRequest toRequest(){
        return CreateVacationRequest.builder()
                .userId(id)
                .days(days)
                .daysLeft(daysLeft)
                .build();
    }

}
