package com.akshay.employee.repository;

import com.akshay.employee.dto.EmployeeDTO;
import com.akshay.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContainingIgnoreCase(String name);

    @Query("""
            SELECT e FROM Employee e
            WHERE e.id = : id
            """)
    Employee getEmployee(Long id);

    @Query("""
            SELECT e FROM Employee e
            WHERE e.email = :email
            """)
    Boolean getValidEmployee(String email);
}
