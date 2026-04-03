package com.akshay.employee.repository;

import com.akshay.employee.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, UUID> {

}
