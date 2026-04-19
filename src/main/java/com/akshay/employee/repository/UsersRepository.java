package com.akshay.employee.repository;

import com.akshay.employee.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, String> {
    @Query("""
            SELECT u FROM UserEntity u
            WHERE u.username = :username
            """)
    public UserEntity getUserByUsername(String username);

    boolean existsByUsername(String username);

}
