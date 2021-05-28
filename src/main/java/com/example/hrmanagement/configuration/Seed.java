package com.example.hrmanagement.configuration;


import com.example.hrmanagement.domain.dao.DepartmentDao;
import com.example.hrmanagement.domain.dao.RoleDao;
import com.example.hrmanagement.domain.entities.Department;
import com.example.hrmanagement.domain.entities.Role;
import com.example.hrmanagement.domain.enums.DeptConst;
import com.example.hrmanagement.domain.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class Seed {


    private final RoleDao roleDao;
    private final DepartmentDao departmentDao;

    public Seed(RoleDao roleDao, DepartmentDao departmentDao) {
        this.roleDao = roleDao;
        this.departmentDao = departmentDao;
    }

    @Autowired


    @PostConstruct
    public void Seeder(){
        Optional<Role>  adminRole = roleDao.findByRole(ERole.ROLE_ADMIN);
        Optional<Role>  userRole = roleDao.findByRole(ERole.ROLE_USER);
        Optional<Role>  managerRole = roleDao.findByRole(ERole.ROLE_MANAGER);
        Optional<Department> hrDept = departmentDao.findByDept(DeptConst.HR);
        Optional<Department> qaDept = departmentDao.findByDept(DeptConst.QA);
        Optional<Department> devDept = departmentDao.findByDept(DeptConst.DEV);
        Optional<Department> allDept = departmentDao.findByDept(DeptConst.ALL);

        if(adminRole.isEmpty()){
            createRole(ERole.ROLE_ADMIN);
        }
        if(userRole.isEmpty()){
            createRole(ERole.ROLE_USER);
        }
        if(managerRole.isEmpty()){
            createRole(ERole.ROLE_MANAGER);
        }
        if(hrDept.isEmpty()){
            createDepartment(DeptConst.HR);
        }
        if(qaDept.isEmpty()){
            createDepartment(DeptConst.QA);
        }
        if(devDept.isEmpty()){
            createDepartment(DeptConst.DEV);
        }
        if(allDept.isEmpty()){
            createDepartment(DeptConst.ALL);
        }

    }

    public Role createRole(ERole role){
        return roleDao.saveRecord(Role.builder().name(role).build());

    }

    public Department createDepartment(DeptConst deptConst){
        return departmentDao.saveRecord(Department.builder().dept(deptConst).build());
    }


}
