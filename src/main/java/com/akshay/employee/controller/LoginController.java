package com.akshay.employee.controller;

import com.akshay.employee.dto.LoginDTO;
import com.akshay.employee.entity.Employee;
import com.akshay.employee.entity.UserEntity;
import com.akshay.employee.repository.EmployeeRepository;
import com.akshay.employee.service.AdminService;
import com.akshay.employee.service.EmployeeService;
import com.akshay.employee.service.UserService;
import com.akshay.employee.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        UserEntity user = userService.getValidUser(loginDTO.getUsername());

        if (user == null || !passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtils.generateToken(user.getUsername());

        return ResponseEntity.ok(token);
    }



}
