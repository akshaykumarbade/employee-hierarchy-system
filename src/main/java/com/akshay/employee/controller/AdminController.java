package com.akshay.employee.controller;

import com.akshay.employee.dto.EmployeeDTO;
import com.akshay.employee.entity.Employee;
import com.akshay.employee.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/addEmployee")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(adminService.createEmployee(employeeDTO));
    }

    @GetMapping("/allEmployees")
    public ResponseEntity<List<Employee>> getAllEmployee(@RequestParam Long employeeId) {
        List<Employee> allEmployees = adminService.getAllEmployeesForAdminOnly(employeeId);
        if(allEmployees.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "access denied");
        }
        return ResponseEntity.ok(adminService.getAllEmployeesForAdminOnly(employeeId));
    }
}
