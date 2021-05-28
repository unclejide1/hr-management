package com.example.hrmanagement.usecases.admin.impl;

import com.example.hrmanagement.domain.dao.*;
import com.example.hrmanagement.domain.entities.*;
import com.example.hrmanagement.domain.enums.DeptConst;
import com.example.hrmanagement.domain.enums.GenderTypeConstant;
import com.example.hrmanagement.domain.enums.RecordStatusConstant;
import com.example.hrmanagement.infrastructure.exceptions.CustomException;
import com.example.hrmanagement.usecases.admin.ManageEmployeeUseCase;

import com.example.hrmanagement.usecases.auth.impl.SignUpUseCaseImpl;
import com.example.hrmanagement.usecases.dto.request.CreateVacationRequest;
import com.example.hrmanagement.usecases.dto.request.EnrollSalaryRequest;
import com.example.hrmanagement.usecases.dto.request.ManageEmployeeRequest;
import com.example.hrmanagement.usecases.dto.request.UpdateEmployeeDetailsRequest;
import com.example.hrmanagement.usecases.dto.response.EmployeeDetailsResponse;
import com.example.hrmanagement.usecases.dto.response.ManageEmployeeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ManageEmployeeUseCaseImpl implements ManageEmployeeUseCase {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final DepartmentDao departmentDao;
    private final SalaryDao salaryDao;
    private final SalaryHistoryDao salaryHistoryDao;
    private final BonusDao bonusDao;
    private final VacationDao vacationDao;

    @Autowired
    public ManageEmployeeUseCaseImpl(UserDao userDao, RoleDao roleDao, DepartmentDao departmentDao, SalaryDao salaryDao, SalaryHistoryDao salaryHistoryDao, BonusDao bonusDao, VacationDao vacationDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.departmentDao = departmentDao;
        this.salaryDao = salaryDao;
        this.salaryHistoryDao = salaryHistoryDao;
        this.bonusDao = bonusDao;
        this.vacationDao = vacationDao;
    }

    @Override
    public String removeEmployee(ManageEmployeeRequest manageEmployeeRequest) {
        if (!userDao.existsByUsername(manageEmployeeRequest.getUsername())) {
            throw new CustomException("There is no user with this username: " + manageEmployeeRequest.getUsername(), HttpStatus.NOT_FOUND);
        }

        User existingUser = userDao.getUserByUsername(manageEmployeeRequest.getUsername());
        existingUser.setRecordStatus(RecordStatusConstant.DELETED);
        userDao.saveRecord(existingUser);

        return "user successfully deleted";
    }

    @Override
    public ManageEmployeeResponse manageEmployee(ManageEmployeeRequest manageEmployeeRequest, String username) {
        User initiator = userDao.getUserByUsername(username);
        if(!initiator.getDepartments().getDept().equals(DeptConst.ALL)){
            throw new CustomException("you are not authorized to perform this operation", HttpStatus.UNAUTHORIZED);
        }
        if (!userDao.existsByUsername(manageEmployeeRequest.getUsername())) {
            throw new CustomException("There is no user with this username: " + manageEmployeeRequest.getUsername(), HttpStatus.NOT_FOUND);
        }

        User existingUser = userDao.getUserByUsername(manageEmployeeRequest.getUsername());
        Set<String> strRoles = manageEmployeeRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles != null ) {
            SignUpUseCaseImpl.addRoles(strRoles, roles, roleDao);
        }

        Department department = null;
        if(manageEmployeeRequest.getDept() != null && !manageEmployeeRequest.getDept().isBlank()){
            department = departmentDao.getDepartmentByDeptConst(DeptConst.valueOf(manageEmployeeRequest.getDept().toUpperCase()));
        }
        if(department != null){
            existingUser.setDepartments(department);
        }
        existingUser.setRoles(roles);
        User updatedUser = userDao.saveRecord(existingUser);
        Set<String> updatedRoles = new HashSet<>();
        updatedUser.getRoles().forEach(role -> updatedRoles.add(role.getName().name()));
        ManageEmployeeResponse manageEmployeeResponse = ManageEmployeeResponse.builder()
                .username(updatedUser.getUsername())
                .roles(updatedRoles)
                .dept(updatedUser.getDepartments().getDept().name())
                .build();

        return manageEmployeeResponse;
    }

    @Override
    public List<EmployeeDetailsResponse>  getSubordinates(String username) {
        User manager = userDao.getUserByUsername(username);
        Department managerDepartment = manager.getDepartments();
        return getEmployeeDetailsResponses(username, managerDepartment);
    }

    private List<EmployeeDetailsResponse> getEmployeeDetailsResponses(String username, Department managerDepartment) {
        List<User> subordinates = userDao.getAllUsersByDept(managerDepartment);
        List<User> filteredList = subordinates.stream().filter(user -> !user.getUsername().equalsIgnoreCase(username))
                .collect(Collectors.toList());

        return getEmployeeDetailsResponsess(filteredList);
    }

    @Override
    public List<EmployeeDetailsResponse> getAllEmployeesByDept(String dept, String username) {
        DeptConst deptConst = DeptConst.valueOf(dept.toUpperCase());
        System.out.println(deptConst);
        System.out.println(deptConst.equals(DeptConst.HR));
        if(deptConst.equals(DeptConst.HR)){
            throw new CustomException("You don't have access to this resource", HttpStatus.UNAUTHORIZED);
        }

        Department department = departmentDao.getDepartmentByDeptConst(deptConst);
        return getEmployeeDetailsResponses(username, department);

    }

    @Override
    public EmployeeDetailsResponse getEmployeeUnderAManager(Long id, String username) {
        User manager = userDao.getUserByUsername(username);
        Department managerDepartment = manager.getDepartments();
        User foundUser = userDao.getRecordById(id);
        Department foundUserDepartment = null;
        if(foundUser.getDepartments() != null) {
           foundUserDepartment = foundUser.getDepartments();
            if(!managerDepartment.getDept().equals(foundUserDepartment.getDept())  ){
                throw new CustomException("this user is not under you", HttpStatus.UNAUTHORIZED);
            }
        }

        List<Vacation> vacations = vacationDao.getAllVacationsByUser(foundUser);
        List<Bonus> bonuses = bonusDao.getAllBonusByUser(foundUser);
        Bonus foundUserBonus = null;
        Vacation userVacation = null;
        if(vacations.size()>0){
            userVacation = vacations.get(0);
        }
        if(bonuses.size() > 0){
            foundUserBonus = bonuses.get(0);
        }

        Salary foundUserSalary = salaryDao.getSalaryByUser(foundUser);
        EmployeeDetailsResponse employeeDetailsResponse = EmployeeDetailsResponse.builder()
                .id(foundUser.getId())
                .username(foundUser.getUsername())
                .firstName(foundUser.getFirstName())
                .middleName(foundUser.getMiddleName())
                .lastName(foundUser.getLastName())
                .dateOfBirth(foundUser.getDateOfBirth())
                .gender(foundUser.getGender().name())
                .phoneNumber(foundUser.getPhoneNumber())
                .build();
        if(foundUser.getDepartments() != null) {
            foundUserDepartment = foundUser.getDepartments();
            employeeDetailsResponse.setDept(foundUserDepartment.getDept().name());
        }
        if(userVacation !=null){
            employeeDetailsResponse.setVacationDays(userVacation.getDays());
        }
        if(foundUserSalary.getAmount() != null){
            employeeDetailsResponse.setSalaryAmount(foundUserSalary.getAmount());
        }
        if(foundUserBonus != null){
            employeeDetailsResponse.setBonusAmount(foundUserBonus.getAmount());
        }
        if(foundUserDepartment != null){
            employeeDetailsResponse.setDept(foundUserDepartment.getDept().name());
        }


        return employeeDetailsResponse;
    }

    @Override
    public List<EmployeeDetailsResponse> getAllEmployees() {
        List<User> employees = userDao.getAllUsers();
        System.out.println(employees);
        System.out.println("got hia>>>>>>>>");
        for(User user: employees){
            System.out.println(user.getId());
            System.out.println(user.getDepartments().getDept());
        }
        List<User> filteredEmployees = employees.stream().filter(user -> !user.getDepartments().getDept().equals(DeptConst.HR))
                .collect(Collectors.toList());
        return getEmployeeDetailsResponsess(filteredEmployees);

    }

    private List<EmployeeDetailsResponse> getEmployeeDetailsResponsess(List<User> filteredEmployees) {
        List<EmployeeDetailsResponse> employeeDetailsResponses = new ArrayList<>();
        System.out.println("got here>>>>>>>>");
        filteredEmployees.forEach(user -> {
            Department foundUserDepartment = null;
            System.out.println("got here2>>>>>>>>");
            List<Vacation> vacations = vacationDao.getAllVacationsByUser(user);
            System.out.println("got here3>>>>>>>>");
            List<Bonus> bonuses = bonusDao.getAllBonusByUser(user);
            System.out.println("got here4>>>>>>>>");
            Bonus foundUserBonus = null;
            Vacation userVacation = null;
            if(vacations.size()>0){
                userVacation = vacations.get(0);
            }
            if(bonuses.size() > 0){
                foundUserBonus = bonuses.get(0);
            }

            Salary foundUserSalary = salaryDao.getSalaryByUser(user);
            EmployeeDetailsResponse employeeDetailsResponse = EmployeeDetailsResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .firstName(user.getFirstName())
                    .middleName(user.getMiddleName())
                    .lastName(user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
                    .gender(user.getGender().name())
                    .phoneNumber(user.getPhoneNumber())
                    .build();
            if(user.getDepartments() != null) {
                foundUserDepartment = user.getDepartments();
                employeeDetailsResponse.setDept(foundUserDepartment.getDept().name());
            }
            if(userVacation !=null){
                employeeDetailsResponse.setVacationDays(userVacation.getDays());
            }
            if(foundUserSalary.getAmount() != null){
                employeeDetailsResponse.setSalaryAmount(foundUserSalary.getAmount());
            }
            if(foundUserBonus != null){
                employeeDetailsResponse.setBonusAmount(foundUserBonus.getAmount());
            }
            employeeDetailsResponses.add(employeeDetailsResponse);
        });
        return employeeDetailsResponses;
    }


    @Override
    public EmployeeDetailsResponse getEmployee(Long id) {
        User foundUser = userDao.getRecordById(id);
        Department foundUserDepartment = null;
        if(foundUser.getDepartments() != null) {
            foundUserDepartment = foundUser.getDepartments();

        if(foundUserDepartment.getDept().equals(DeptConst.HR)  ){
            throw new CustomException("You don't have access to this resource", HttpStatus.UNAUTHORIZED);
        }
        }


        List<Vacation> vacations = vacationDao.getAllVacationsByUser(foundUser);
        List<Bonus> bonuses = bonusDao.getAllBonusByUser(foundUser);
        Bonus foundUserBonus = null;
        Vacation userVacation = null;
        if(vacations.size()>0){
            userVacation = vacations.get(0);
        }
        if(bonuses.size() > 0){
            foundUserBonus = bonuses.get(0);
        }

        Salary foundUserSalary = salaryDao.getSalaryByUser(foundUser);
        EmployeeDetailsResponse employeeDetailsResponse = EmployeeDetailsResponse.builder()
                .id(foundUser.getId())
                .username(foundUser.getUsername())
                .firstName(foundUser.getFirstName())
                .middleName(foundUser.getMiddleName())
                .lastName(foundUser.getLastName())
                .dateOfBirth(foundUser.getDateOfBirth())
                .gender(foundUser.getGender().name())
                .phoneNumber(foundUser.getPhoneNumber())
                .build();
        if(foundUser.getDepartments() != null) {
            foundUserDepartment = foundUser.getDepartments();
            employeeDetailsResponse.setDept(foundUserDepartment.getDept().name());
        }
        if(userVacation !=null){
            employeeDetailsResponse.setVacationDays(userVacation.getDays());
        }
        if(foundUserSalary.getAmount() != null){
            employeeDetailsResponse.setSalaryAmount(foundUserSalary.getAmount());
        }
        if(foundUserBonus != null){
            employeeDetailsResponse.setBonusAmount(foundUserBonus.getAmount());
        }
        employeeDetailsResponse.setDept(foundUserDepartment.getDept().name());


        return employeeDetailsResponse;
    }

    @Override
    public EmployeeDetailsResponse getEmployeeByUsername(String username) {
        User user = userDao.getUserByUsername(username);

        return getEmployee(user.getId());
    }

    @Override
    public EmployeeDetailsResponse updateEmployeeDetails(UpdateEmployeeDetailsRequest updateEmployeeRequest, String username) {
        User manager = userDao.getUserByUsername(username);
        Department managerDepartment = manager.getDepartments();
        User foundUser = userDao.getRecordById(updateEmployeeRequest.getId());
        Department foundUserDepartment = null;
        if(foundUser.getDepartments() != null) {
            foundUserDepartment = foundUser.getDepartments();
        }
        if(!managerDepartment.equals(foundUserDepartment)  ){
            throw new CustomException("this user is not under you", HttpStatus.UNAUTHORIZED);
        }

        if(updateEmployeeRequest.getFirstName() != null){
            foundUser.setFirstName(updateEmployeeRequest.getFirstName());
        }
        if(updateEmployeeRequest.getLastName() != null){
            foundUser.setLastName(updateEmployeeRequest.getLastName());
        }
        if(updateEmployeeRequest.getMiddleName() != null){
            foundUser.setMiddleName(updateEmployeeRequest.getMiddleName());
        }
        if(updateEmployeeRequest.getDateOfBirth() != null){
            foundUser.setDateOfBirth(updateEmployeeRequest.getDateOfBirth());
        }
        if(updateEmployeeRequest.getPhoneNumber() != null){
            foundUser.setPhoneNumber(updateEmployeeRequest.getPhoneNumber());
        }
        if(updateEmployeeRequest.getGender() != null){
            foundUser.setGender(GenderTypeConstant.valueOf(updateEmployeeRequest.getGender().toUpperCase()));
        }


        User updatedUser = userDao.saveRecord(foundUser);
        EmployeeDetailsResponse employeeDetailsResponse = EmployeeDetailsResponse.builder()
                .id(updatedUser.getId())
                .username(updatedUser.getUsername())
                .firstName(updatedUser.getFirstName())
                .middleName(updatedUser.getMiddleName())
                .lastName(updatedUser.getLastName())
                .dateOfBirth(updatedUser.getDateOfBirth())
                .gender(updatedUser.getGender().name())
                .phoneNumber(updatedUser.getPhoneNumber())
                .build();
        if(updatedUser.getDepartments().getDept()  != null) {
            employeeDetailsResponse.setDept(foundUserDepartment.getDept().name());
        }
        return employeeDetailsResponse;
    }

    @Override
    public String enrollEmployeeSalary(EnrollSalaryRequest enrollSalaryRequest) {
        User foundUser = userDao.getRecordById(enrollSalaryRequest.getUserId());
        BigDecimal salaryAmount = enrollSalaryRequest.getAmount();
        if (salaryAmount.compareTo(BigDecimal.ZERO) <= 0){
            salaryAmount = BigDecimal.ZERO;
        }
        Salary foundUserSalary = Salary.builder()
                .user(foundUser)
                .amount(salaryAmount).build();
        foundUserSalary = salaryDao.saveRecord(foundUserSalary);

        return "created salary successfully";
    }

    @Override
    public String createVacation(CreateVacationRequest createVacationRequest) {
        User foundUser = userDao.getRecordById(createVacationRequest.getUserId());
        Vacation foundUserVacation = Vacation.builder()
                .user(foundUser)
                .days(createVacationRequest.getDays())
                .daysLeft(createVacationRequest.getDaysLeft())
                .build();
        foundUserVacation = vacationDao.saveRecord(foundUserVacation);
        return "vacation created for user";
    }

    @Override
    public String createEmployeeBonus(EnrollSalaryRequest enrollSalaryRequest) {
        User foundUser = userDao.getRecordById(enrollSalaryRequest.getUserId());
        BigDecimal bonusAmount = enrollSalaryRequest.getAmount();
        if (bonusAmount.compareTo(BigDecimal.ZERO) <= 0){
            bonusAmount = BigDecimal.ZERO;
        }
        Bonus foundUserBonus = Bonus.builder()
                .user(foundUser)
                .amount(bonusAmount)
                .build();
        foundUserBonus = bonusDao.saveRecord(foundUserBonus);
        return "Bonus has been created for user";
    }


}
