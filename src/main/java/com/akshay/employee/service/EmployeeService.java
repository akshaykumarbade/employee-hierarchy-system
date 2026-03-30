package com.akshay.employee.service;

import com.akshay.employee.entity.Employee;
import com.akshay.employee.entity.EmployeeHierarchy;
import com.akshay.employee.repository.EmployeeHierarchyRepository;
import com.akshay.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeHierarchyRepository hierarchyRepository;

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

    public List<Employee> getSubordinates(UUID managerId) {
        return hierarchyRepository.findSubordinates(managerId);
    }

    public List<Employee> search(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }
}
