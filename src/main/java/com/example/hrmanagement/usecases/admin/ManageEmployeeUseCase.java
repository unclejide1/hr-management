package com.example.hrmanagement.usecases.admin;


import com.example.hrmanagement.usecases.dto.request.CreateVacationRequest;
import com.example.hrmanagement.usecases.dto.request.EnrollSalaryRequest;
import com.example.hrmanagement.usecases.dto.request.ManageEmployeeRequest;
import com.example.hrmanagement.usecases.dto.request.UpdateEmployeeDetailsRequest;
import com.example.hrmanagement.usecases.dto.response.EmployeeDetailsResponse;
import com.example.hrmanagement.usecases.dto.response.ManageEmployeeResponse;

import java.util.List;

public interface ManageEmployeeUseCase {
  String removeEmployee(ManageEmployeeRequest manageEmployeeRequest);
  ManageEmployeeResponse manageEmployee(ManageEmployeeRequest manageEmployeeRequest, String username);
  List<EmployeeDetailsResponse> getSubordinates(String username);
  List<EmployeeDetailsResponse> getAllEmployeesByDept(String dept, String username);
  EmployeeDetailsResponse getEmployeeUnderAManager(Long id, String username);
  List<EmployeeDetailsResponse> getAllEmployees();
  EmployeeDetailsResponse getEmployee(Long id);
  EmployeeDetailsResponse getEmployeeByUsername(String username);
  EmployeeDetailsResponse updateEmployeeDetails(UpdateEmployeeDetailsRequest updateEmployeeRequest, String username);
  String enrollEmployeeSalary(EnrollSalaryRequest enrollSalaryRequest);
  String createVacation(CreateVacationRequest createVacationRequest);
  String createEmployeeBonus(EnrollSalaryRequest enrollSalaryRequest);

}
