package com.akshay.employee.service;

import com.akshay.employee.entity.*;
import com.akshay.employee.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeHierarchyRepository hierarchyRepository;
    private final EmployeeRoleRepository employeeRoleRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public Employee createEmployee(Employee employee) {



        Employee saved = employeeRepository.save(employee);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());

        // 1. Self reference
        hierarchyRepository.save(
                new EmployeeHierarchy(saved.getId(), saved.getId(), 0)
        );

        // 2. If manager exists → inherit hierarchy
        if (employee.getManagerId() != null) {
            List<EmployeeHierarchy> managerHierarchy =
                    hierarchyRepository.findAll().stream()
                            .filter(h -> h.getDescendantId().equals(employee.getManagerId()))
                            .toList();

            for (EmployeeHierarchy h : managerHierarchy) {
                hierarchyRepository.save(
                        new EmployeeHierarchy(
                                h.getAncestorId(),
                                saved.getId(),
                                h.getDepth() + 1
                        )
                );
            }
        }

        return saved;
    }

    public List<Employee> getSubordinates(Long managerId) {
        return hierarchyRepository.findSubordinates(managerId);
    }

    public List<Employee> search(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public List<Employee> getDirectSubordinates(Long managerId) {
        return hierarchyRepository.findDirectSubordinates(managerId);
    }

    public List<Employee> getAllEmployeesForAdminOnly(Long adminId) {
        EmployeeRole employeeRole = employeeRoleRepository.getEmployeeRole(adminId);
        UUID roleId = employeeRole.getRoleId();
        Role role = roleRepository.getRole(roleId);
        String roleName = role.getName();
        String permission = permissionRepository.getPermission(role.getPermissionId()).getName();

        List<Employee> allEmployees = new ArrayList<>();
        if(roleName.equalsIgnoreCase("Admin") && permission.equalsIgnoreCase("ALL")) {
            allEmployees = employeeRepository.findAll();
        }
        return allEmployees;
    }
}
