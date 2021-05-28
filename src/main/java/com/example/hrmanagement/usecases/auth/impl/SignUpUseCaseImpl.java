package com.example.hrmanagement.usecases.auth.impl;


import com.example.hrmanagement.domain.dao.DepartmentDao;
import com.example.hrmanagement.domain.dao.RoleDao;
import com.example.hrmanagement.domain.dao.UserDao;
import com.example.hrmanagement.domain.entities.Department;
import com.example.hrmanagement.domain.entities.Role;
import com.example.hrmanagement.domain.entities.User;
import com.example.hrmanagement.domain.enums.DeptConst;
import com.example.hrmanagement.domain.enums.ERole;
import com.example.hrmanagement.domain.enums.GenderTypeConstant;
import com.example.hrmanagement.infrastructure.exceptions.CustomException;
import com.example.hrmanagement.usecases.auth.SignUpUseCase;
import com.example.hrmanagement.usecases.dto.request.SignUpRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Named;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Named
@AllArgsConstructor
public class SignUpUseCaseImpl implements SignUpUseCase {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder encoder;
    private final DepartmentDao departmentDao;



    @Override
    public String createUser(SignUpRequest signUpRequest) {
        if (userDao.existsByUsername(signUpRequest.getUsername())) {
           throw new CustomException("A user already exists with this username: " + signUpRequest.getUsername(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if(signUpRequest.getDateOfBirth() != null){
            long age = LocalDate.now().getYear() - signUpRequest.getDateOfBirth().getYear();
            if(age < 18){
                throw  new CustomException("Must be above 18 to register on this platform", HttpStatus.BAD_REQUEST);
            }
        }
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleDao.findByRole(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            addRoles(strRoles, roles, roleDao);
        }

        Department department = null;
        if(signUpRequest.getDept() != null && !signUpRequest.getDept().isBlank()){
            department = departmentDao.getDepartmentByDeptConst(DeptConst.valueOf(signUpRequest.getDept().toUpperCase()));
        }
        System.out.println(department);

        // Create new user's account
        User user = User.builder().username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .dateOfBirth(signUpRequest.getDateOfBirth())
                .firstName(signUpRequest.getFirstName())
                .middleName(signUpRequest.getMiddleName())
                .lastName(signUpRequest.getLastName())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .gender(GenderTypeConstant.valueOf(signUpRequest.getGender().toUpperCase()))
                .build();

        user.setRoles(roles);
        if(department != null) {
            user.setDepartments(department);
        }
        User savedUser =userDao.saveRecord(user);
        System.out.println(savedUser.toString());

        return "Saved";
    }

    public static void addRoles(Set<String> strRoles, Set<Role> roles, RoleDao roleDao) {
        strRoles.forEach(role -> {
            System.out.println(role);
            if ("ADMIN".equalsIgnoreCase(role)) {
                Role adminRole = roleDao.findByRole(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
            } else if("MANAGER".equalsIgnoreCase(role)){
                Role managerRole = roleDao.findByRole(ERole.ROLE_MANAGER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(managerRole);
            } else {
                Role userRole = roleDao.findByRole(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
        });
    }
}

