package com.akshay.employee.repository;

import com.akshay.employee.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    @Query(
            """
                    select p from Permission p
                    where p.id = :permissionId
                    """
    )
    public Permission getPermission(UUID permissionId);
}
