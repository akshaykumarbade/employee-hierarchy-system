package com.akshay.employee.repository;

import com.akshay.employee.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    @Query(
            """
                    select r from Role r
                    where r.id = :roleId
                    """
    )
    public Role getRole(UUID roleId);

    @Query(
            """
                    select r from Role r
                    where r.name = :roleName
                    """
    )
    public Role getRoleByName(String roleName);
}
