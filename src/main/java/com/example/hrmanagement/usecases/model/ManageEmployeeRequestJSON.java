package com.example.hrmanagement.usecases.model;


import com.example.hrmanagement.usecases.dto.request.ManageEmployeeRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.Set;

@Data
public class ManageEmployeeRequestJSON {
    @Email(message = "Username must be an email")
    @NotBlank(message = "Username is Required")
    private String username;
    @ApiModelProperty(notes = "Types: ADMIN,admin,USER,user,MANAGER,manager")
//    @Pattern(regexp = "(ADMIN|admin|USER|user|MANAGER|manager)")
    private Set<String> roles;
    @ApiModelProperty(notes = "Types: QA,qa,DEV,dev,HR,hr")
    private String dept;

    public ManageEmployeeRequest toRequest(){
        return ManageEmployeeRequest.builder().username(username)
                .roles(roles)
                .dept(dept)
                .build();
    }
}
