package com.akshay.employee.service;

import com.akshay.employee.dto.EmployeeDTO;
import com.akshay.employee.dto.RoleDTO;
import com.akshay.employee.entity.Employee;
import com.akshay.employee.entity.EmployeeHierarchy;
import com.akshay.employee.entity.Role;
import com.akshay.employee.entity.UserEntity;
import com.akshay.employee.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    public static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final EmployeeHierarchyRepository hierarchyRepository;
    private final UsersRepository usersRepository;

    public Employee createEmployee(EmployeeDTO employeeDTO) {
        System.out.println("Employee ID from DTO: " + employeeDTO.getEmployeeId());
        Long managerId = employeeDTO.getManagerId();
        UUID roleId = roleRepository.getRoleByName(employeeDTO.getRole()).getId();
        Employee employee = Employee.builder()
                .id(employeeDTO.getEmployeeId())
                .name(employeeDTO.getName())
                .email(employeeDTO.getEmail())
                .managerId(managerId)
                .roleId(roleId)
                .createdAt(LocalDateTime.now())
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeHierarchy employeeHierarchy = EmployeeHierarchy.builder()
                .ancestorId(managerId)
                .descendantId(savedEmployee.getId())
                .depth(managerId != null ? 1 : 0)
                .build();
        hierarchyRepository.save(employeeHierarchy);
        return employee;
    }

    public Employee updateEmployee(EmployeeDTO employeeDTO) {
        Long employeeId = employeeDTO.getEmployeeId();
        Employee employee;
        try {
            employee = employeeRepository.getEmployee(employeeId);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Employee {} not found", employeeDTO.getName());
        }
        Long managerId = employeeDTO.getManagerId();
        UUID roleId = roleRepository.getRoleByName(employeeDTO.getRole()).getId();
        employee = Employee.builder()
                .id(employeeDTO.getEmployeeId())
                .name(employeeDTO.getName())
                .email(employeeDTO.getEmail())
                .managerId(managerId)
                .roleId(roleId)
                .createdAt(LocalDateTime.now())
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeHierarchy employeeHierarchy = EmployeeHierarchy.builder()
                .ancestorId(managerId)
                .descendantId(savedEmployee.getId())
                .depth(managerId != null ? 1 : 0)
                .build();
        hierarchyRepository.save(employeeHierarchy);
        return employee;
    }

    public List<Employee> getAllEmployeesForAdminOnly(Long adminId) {
        UUID roleId = employeeRepository.getEmployee(adminId).getRoleId();
        Role role = roleRepository.getRole(roleId);
        String roleName = role.getName();
        String permission = role.getPermission();

        List<Employee> allEmployees = new ArrayList<>();
        if(roleName.equalsIgnoreCase("Admin") && permission.equalsIgnoreCase("ALL")) {
            allEmployees = employeeRepository.findAll();
        }
        return allEmployees;
    }

    public Role createRole(RoleDTO roleRequest) {
        Role role = Role.builder().name(roleRequest.getName()).permission(roleRequest.getPermission()).build();
        roleRepository.save(role);
        return role;
    }

    public List<Role> getAllRolesForAdmin(Long adminId) {

        UUID roleId = employeeRepository.getEmployee(adminId).getRoleId();
        Role role = roleRepository.getRole(roleId);
        List<Role> allRoles = new ArrayList<>();
        if(role.getName().equalsIgnoreCase("Admin") && role.getPermission().equalsIgnoreCase("ALL")) {
            allRoles = roleRepository.findAll();
        }
        return allRoles;
    }

    public List<Employee> getAllSubordinates(Long employeeId) {
        return hierarchyRepository.findSubordinates(employeeId);
    }

    public Employee deleteEmployee(EmployeeDTO employeeDTO) {
        Long employeeId = employeeDTO.getEmployeeId();
        Employee employee = employeeRepository.getEmployee(employeeId);
        employeeRepository.delete(employee);
        return employee;
    }


}
