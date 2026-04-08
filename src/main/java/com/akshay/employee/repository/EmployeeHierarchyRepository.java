package com.akshay.employee.repository;

import com.akshay.employee.entity.Employee;
import com.akshay.employee.entity.EmployeeHierarchy;
import com.akshay.employee.entity.EmployeeHierarchyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeHierarchyRepository extends JpaRepository<EmployeeHierarchy, EmployeeHierarchyId> {

    @Query("""
        SELECT e FROM Employee e
        JOIN EmployeeHierarchy h ON e.id = h.descendantId
        WHERE h.ancestorId = :managerId AND h.depth > 0
    """)
    List<Employee> findSubordinates(Long managerId);

    @Query("""
        SELECT e FROM Employee e
        JOIN EmployeeHierarchy h ON e.id = h.descendantId
        WHERE h.ancestorId = :managerId AND h.depth = 1
    """)
    List<Employee> findDirectSubordinates(Long employeeId);

    @Query("""
            SELECT h FROM EmployeeHierarchy h
            WHERE h.descendantId = : employeeId AND h.depth = 1
            """)
    EmployeeHierarchy getEmployeeHierarchy(Long employeeId);
}
