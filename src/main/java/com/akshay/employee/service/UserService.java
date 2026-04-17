package com.akshay.employee.service;

import com.akshay.employee.entity.UserEntity;
import com.akshay.employee.repository.EmployeeHierarchyRepository;
import com.akshay.employee.repository.EmployeeRepository;
import com.akshay.employee.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final EmployeeRepository employeeRepository;
    private final UsersRepository usersRepository;
    private final EmployeeHierarchyRepository employeeHierarchyRepository;

    public UserEntity getValidUser(String userName, String password) {
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
