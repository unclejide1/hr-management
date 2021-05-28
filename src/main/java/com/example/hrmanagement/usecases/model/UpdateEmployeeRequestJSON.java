package com.example.hrmanagement.usecases.model;

import com.example.hrmanagement.usecases.dto.request.SignUpRequest;
import com.example.hrmanagement.usecases.dto.request.UpdateEmployeeDetailsRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Data
public class UpdateEmployeeRequestJSON {
    @ApiModelProperty(notes = "valid user ID", required = true)
    @NotNull(message = "must pass an id")
    private Long id;

    @ApiModelProperty(notes = "valid email")
    @Email(message = "Username must be an email")
    private String username;

    @ApiModelProperty(notes = "first name")
    private String firstName;

    @ApiModelProperty(notes = "last name")
    private String lastName;

    @ApiModelProperty(notes = "middle name")
    private String middleName;

    @ApiModelProperty(notes = "Format: dd/MM/yyyy")
    private String dateOfBirth;

    @ApiModelProperty(notes = "phone number")
    private String phoneNumber;

    @ApiModelProperty(notes = "Types: MALE,male,FEMALE,female")
    @Pattern(regexp = "(MALE|male|FEMALE|female)")
    private String gender;



    public UpdateEmployeeDetailsRequest toRequest(){
        return UpdateEmployeeDetailsRequest.builder().username(username)
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .dateOfBirth(LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .phoneNumber(phoneNumber)
                .gender(gender)
                .build();
    }
}
