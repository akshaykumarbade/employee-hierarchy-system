package com.akshay.employee.controller;

import com.akshay.employee.entity.Employee;
import com.akshay.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> search(@RequestParam String name) {
        return ResponseEntity.ok(employeeService.search(name));
    }

    @GetMapping("/{id}/subordinates")
    public ResponseEntity<List<Employee>> getSubordinates(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeService.getSubordinates(id));
    }
}
