package com.example.hrmanagement.infrastructure.web.controllers.admin;


import com.example.hrmanagement.infrastructure.apiresponse.ApiResponse;
import com.example.hrmanagement.usecases.admin.ManageEmployeeUseCase;
import com.example.hrmanagement.usecases.auth.SignUpUseCase;
import com.example.hrmanagement.usecases.dto.response.EmployeeDetailsResponse;
import com.example.hrmanagement.usecases.dto.response.ManageEmployeeResponse;
import com.example.hrmanagement.usecases.model.CreateEmployeeJSON;
import com.example.hrmanagement.usecases.model.CreateVacationRequestJSON;
import com.example.hrmanagement.usecases.model.EnrollSalaryRequestJSON;
import com.example.hrmanagement.usecases.model.ManageEmployeeRequestJSON;
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
@Api(tags = "Admin Endpoints", description = "Endpoints for admin operations")
@RestController
@Secured("ROLE_ADMIN")
@RequestMapping(value = "api/v1/admin")
public class AdminController {

    private final SignUpUseCase signUpUseCase;
    private final ManageEmployeeUseCase manageEmployeeUseCase;

    @Autowired
    public AdminController(SignUpUseCase signUpUseCase, ManageEmployeeUseCase manageEmployeeUseCase) {
        this.signUpUseCase = signUpUseCase;
        this.manageEmployeeUseCase = manageEmployeeUseCase;
    }




    @ApiOperation(value = "create employee", notes = "This endpoint is used to create an employee", authorizations = { @Authorization(value="Authorization") })
    @PostMapping(value = "/createEmployee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> create(@Valid @RequestBody CreateEmployeeJSON requestJSON){
        String response = signUpUseCase.createUser(requestJSON.toRequest());
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.CREATED);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully created a user");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }

    @ApiOperation(value = "delete employee", notes = "This endpoint is used to remove an employee", authorizations = { @Authorization(value="Authorization") })
    @DeleteMapping(value = "/deleteEmployee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> remove(@Valid @RequestBody ManageEmployeeRequestJSON requestJSON){
        String response = manageEmployeeUseCase.removeEmployee(requestJSON.toRequest());
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.NO_CONTENT);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully removed an employee");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }

    @ApiOperation(value = "manage employee", notes = "This endpoint is used to manage an employee role", authorizations = { @Authorization(value="Authorization") })
    @PutMapping(value = "/manageEmployee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ManageEmployeeResponse>> manage(@Valid @RequestBody ManageEmployeeRequestJSON requestJSON, Principal principal){
        ManageEmployeeResponse response = manageEmployeeUseCase.manageEmployee(requestJSON.toRequest(), principal.getName());
        ApiResponse<ManageEmployeeResponse> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully updated an employee's dept or role");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }

    @ApiOperation(value = "Get all employees", notes = "This endpoint is used to get the details of all employees under a manager", authorizations = { @Authorization(value="Authorization") })
    @GetMapping(value = "/getEmploy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<EmployeeDetailsResponse>>> getAllEmployees(){
        List<EmployeeDetailsResponse> response = manageEmployeeUseCase.getAllEmployees();
        ApiResponse<List<EmployeeDetailsResponse>> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully retrieved employees details");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }

    @ApiOperation(value = "Get an employee", notes = "This endpoint is used to get the details of an employee under a manager", authorizations = { @Authorization(value="Authorization") })
    @GetMapping(value = "/getEmploye/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<EmployeeDetailsResponse>> getEmployee(@PathVariable String id){
        EmployeeDetailsResponse response = manageEmployeeUseCase.getEmployee(Long.valueOf(id));
        System.out.println("response>>>  "+response);
        ApiResponse<EmployeeDetailsResponse> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully retrieved employee detail");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }

    @ApiOperation(value = "Get all employees By dept", notes = "This endpoint is used to get the details of all employees under a manager", authorizations = { @Authorization(value="Authorization") })
    @GetMapping(value = "/getEmployByDept/{dept}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<EmployeeDetailsResponse>>> getAllEmployeesByDept(@PathVariable String dept, Principal principal){
        String username = principal.getName();
        List<EmployeeDetailsResponse> response = manageEmployeeUseCase.getAllEmployeesByDept(dept,username);
        ApiResponse<List<EmployeeDetailsResponse>> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully retrieved employees details");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }

    @ApiOperation(value = "Create Salary for an employees", notes = "This endpoint is used to create salary for ann employee", authorizations = { @Authorization(value="Authorization") })
    @PostMapping(value = "/createSalary", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> createEmployeeSalary(@Valid @RequestBody EnrollSalaryRequestJSON enrollSalaryRequestJSON){
        String response = manageEmployeeUseCase.enrollEmployeeSalary(enrollSalaryRequestJSON.toRequest());
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully retrieved employees details");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }

    @ApiOperation(value = "Create Vacation for an employees", notes = "This endpoint is used to create Vacation salary for an employee", authorizations = { @Authorization(value="Authorization") })
    @PostMapping(value = "/createVacation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> createEmployeeVacation(@Valid @RequestBody CreateVacationRequestJSON createVacationRequestJSON){
        String response = manageEmployeeUseCase.createVacation(createVacationRequestJSON.toRequest());
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully retrieved employees details");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }

    @ApiOperation(value = "Create Bonus for an employees", notes = "This endpoint is used to create Vacation salary for an employee", authorizations = { @Authorization(value="Authorization") })
    @PostMapping(value = "/createBonus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> createEmployeeBonus(@Valid @RequestBody EnrollSalaryRequestJSON enrollSalaryRequestJSON){
        String response = manageEmployeeUseCase.createEmployeeBonus(enrollSalaryRequestJSON.toRequest());
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully retrieved employees details");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());

    }
}
