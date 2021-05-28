package com.example.hrmanagement.infrastructure.web.controllers.manager;


import com.example.hrmanagement.infrastructure.apiresponse.ApiResponse;
import com.example.hrmanagement.usecases.admin.ManageEmployeeUseCase;
import com.example.hrmanagement.usecases.dto.request.UpdateEmployeeDetailsRequest;
import com.example.hrmanagement.usecases.dto.response.EmployeeDetailsResponse;
import com.example.hrmanagement.usecases.model.UpdateEmployeeRequestJSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Validated
@Api(tags = "Managerial Endpoints", description = "Endpoints for admin operations")
@RestController
@Secured("ROLE_MANAGER")
@RequestMapping(value = "api/v1/manage")
public class ManagerController {


    private final ManageEmployeeUseCase manageEmployeeUseCase;

    @Autowired
    public ManagerController(ManageEmployeeUseCase manageEmployeeUseCase) {
        this.manageEmployeeUseCase = manageEmployeeUseCase;
    }


    @ApiOperation(value = "Get all employees", notes = "This endpoint is used to get the details of all employees under a manager", authorizations = { @Authorization(value="Authorization") })
    @GetMapping(value = "/getEmployees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<EmployeeDetailsResponse>>> getAllEmployees( Principal principal){
        String managerUsername = principal.getName();
        List<EmployeeDetailsResponse> response = manageEmployeeUseCase.getSubordinates(managerUsername);
        ApiResponse<List<EmployeeDetailsResponse>> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully retrieved employees details");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }


    @ApiOperation(value = "Get an employee", notes = "This endpoint is used to get the details of an employee under a manager", authorizations = { @Authorization(value="Authorization") })
    @GetMapping(value = "/getEmployee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<EmployeeDetailsResponse>> getEmployee(@PathVariable String id, Principal principal){
        String managerUsername = principal.getName();
        EmployeeDetailsResponse response = manageEmployeeUseCase.getEmployeeUnderAManager(Long.valueOf(id),managerUsername);
        ApiResponse<EmployeeDetailsResponse> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully retrieved employee detail");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }


    @ApiOperation(value = "Update an employee details", notes = "This endpoint is used to update the details of an employee under a manager", authorizations = { @Authorization(value="Authorization") })
    @PutMapping(value = "/updateEmployee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<EmployeeDetailsResponse>> updateEmployeeDetails(@Valid @RequestBody UpdateEmployeeRequestJSON updateEmployeeRequestJSON, Principal principal){
        String managerUsername = principal.getName();
        EmployeeDetailsResponse response = manageEmployeeUseCase.updateEmployeeDetails(updateEmployeeRequestJSON.toRequest(),managerUsername);
        ApiResponse<EmployeeDetailsResponse> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully updated employee detail");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }


}
