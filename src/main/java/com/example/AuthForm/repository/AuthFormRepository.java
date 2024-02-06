package com.example.AuthForm.repository;

import com.example.AuthForm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthFormRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> getAllUsers();

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    User getUserByEmail(@Param("email") String email);

}
