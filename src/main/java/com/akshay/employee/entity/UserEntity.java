package com.akshay.employee.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class UserEntity {
    @Id
    @Column(unique = true)
    String username;
    String password;

}
