package com.akshay.employee.service;

import com.akshay.employee.dto.EmployeeDTO;
import com.akshay.employee.dto.RoleDTO;
import com.akshay.employee.entity.Employee;
import com.akshay.employee.entity.EmployeeHierarchy;
import com.akshay.employee.entity.EmployeeRole;
import com.akshay.employee.entity.Role;
import com.akshay.employee.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeRoleRepository employeeRoleRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final EmployeeHierarchyRepository hierarchyRepository;

    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Long managerId = employeeDTO.getManagerId();
        Employee employee = Employee.builder()
                .id(employeeDTO.getEmployeeId())
                .name(employeeDTO.getName())
                .email(employeeDTO.getEmail())
                .managerId(managerId)
                .createdAt(LocalDateTime.now())
                .build();

        employeeRepository.save(employee);

        EmployeeHierarchy employeeHierarchy = EmployeeHierarchy.builder()
                .ancestorId(managerId)
                .descendantId(employeeDTO.getEmployeeId())
                .depth(0)
                .build();
        hierarchyRepository.save(employeeHierarchy);
        UUID roleId = roleRepository.getRoleByName(employeeDTO.getRole()).getId();
        EmployeeRole employeeRole = EmployeeRole.builder()
                .employeeId(employeeDTO.getEmployeeId())
                .roleId(roleId)
                .build();
        employeeRoleRepository.save(employeeRole);
        return employee;
    }

    public List<Employee> getAllEmployeesForAdminOnly(Long adminId) {
        EmployeeRole employeeRole = employeeRoleRepository.getEmployeeRole(adminId);
        UUID roleId = employeeRole.getRoleId();
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
}
