package com.akshay.employee.repository;

import com.akshay.employee.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, UUID> {

    @Query(
            """
                    select e from EmployeeRole e
                    where e.employeeId = :employeeId
                    """
    )
    public EmployeeRole getEmployeeRole(Long employeeId);
}
