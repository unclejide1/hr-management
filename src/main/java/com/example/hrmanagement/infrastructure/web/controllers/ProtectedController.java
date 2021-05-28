package com.example.hrmanagement.infrastructure.web.controllers;


import com.example.hrmanagement.infrastructure.apiresponse.ApiResponse;
import com.example.hrmanagement.usecases.admin.ManageEmployeeUseCase;
import com.example.hrmanagement.usecases.dto.response.EmployeeDetailsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Validated
@Api(tags = "Protected Endpoints")
@RestController
@RequestMapping(value = "api/v1/user")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN","ROLE_USER"})
public class ProtectedController {
    private final ManageEmployeeUseCase manageEmployeeUseCase;

    @Autowired
    public ProtectedController(ManageEmployeeUseCase manageEmployeeUseCase) {
        this.manageEmployeeUseCase = manageEmployeeUseCase;
    }


    @ApiOperation(value = "Get details", notes = "This endpoint is used by an employee to get his/her details", authorizations = { @Authorization(value="Authorization") })
    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<EmployeeDetailsResponse>> getEmployee(Principal principal){

        EmployeeDetailsResponse response = manageEmployeeUseCase.getEmployeeByUsername(principal.getName());
        ApiResponse<EmployeeDetailsResponse> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully retrieved employee detail");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }
}
