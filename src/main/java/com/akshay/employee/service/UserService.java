package com.akshay.employee.service;

import com.akshay.employee.entity.UserEntity;
import com.akshay.employee.repository.EmployeeHierarchyRepository;
import com.akshay.employee.repository.EmployeeRepository;
import com.akshay.employee.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final EmployeeRepository employeeRepository;
    private final UsersRepository usersRepository;
    private final EmployeeHierarchyRepository employeeHierarchyRepository;
    private final PasswordEncoder passwordEncoder;

    public String createUser(String email, String password) {
        if (usersRepository.existsByUsername(email)) {
            throw new RuntimeException("User already exists with email: " + email);
        }
        if (employeeRepository.getValidEmployee(email)) {
            UserEntity newUser = UserEntity.builder()
                    .username(email)
                    .password(passwordEncoder.encode(password))
                    .build();
            usersRepository.save(newUser);

            return newUser.getUsername();
        } else {
            LOGGER.error("Employee with {} does not exist", email);
            throw new RuntimeException("Employee does not exist with email: " + email);
        }

    }

    public void updatePassword(String username, String oldPassword, String newPassword) {

        UserEntity user = null;
        try {
            user = usersRepository.getUserByUsername(username);
        } catch (RuntimeException e) {
            LOGGER.error("User not found");
        }

        // Step 1: Verify old password
        if (!passwordEncoder.matches(oldPassword, user != null ? user.getPassword() : null)) {
            throw new RuntimeException("Old password is incorrect");
        }

        // Step 2: Encode new password
        String encodedNewPassword = passwordEncoder.encode(newPassword);

        // Step 3: Update and save
        if (user != null) {
            user.setPassword(encodedNewPassword);
            usersRepository.save(user);
        }
    }

    public UserEntity getValidUser(String userName) {
        UserEntity user = null;
        try{
            user = usersRepository.getUserByUsername(userName);
        } catch (NoSuchElementException e) {
            LOGGER.error("User {} not found", userName);
        }

        if(!employeeRepository.getValidEmployee(userName)) {
            LOGGER.error("User is not a registered Employee of this organization. Contact Admin");
        }

        return user;

    }

}
