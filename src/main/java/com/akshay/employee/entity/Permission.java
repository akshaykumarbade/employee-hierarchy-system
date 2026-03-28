package com.akshay.employee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "permission")
public class Permission implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
}
