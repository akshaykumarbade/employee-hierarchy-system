package com.akshay.employee.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users", schema = "employee_hierarchy_system")
@Builder
public class users {
    Long username;
    String password;

}
