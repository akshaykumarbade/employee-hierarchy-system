package com.akshay.employee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "employee_role")
@Data
@IdClass(EmployeeRoleId.class)
public class EmployeeRole {

    @Id
    private UUID employeeId;

    @Id
    private UUID roleId;
}
