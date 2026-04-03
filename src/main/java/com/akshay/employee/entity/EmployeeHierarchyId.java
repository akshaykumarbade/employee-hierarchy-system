package com.akshay.employee.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class EmployeeHierarchyId implements Serializable {

    private Long ancestorId;
    private Long descendantId;
}
