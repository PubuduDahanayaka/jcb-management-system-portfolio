package com.example.demo.user_management.repository;

import com.example.demo.user_management.entity.User;
import com.example.demo.user_management.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByUserIdAndIsActiveTrue(String userId);
    
    List<User> findByUserType(UserType userType);
    
    List<User> findByIsActiveTrue();
    
    @Query("SELECT u FROM User u WHERE u.userId = :userId AND u.password = :password AND u.isActive = true")
    Optional<User> findByUserIdAndPasswordAndIsActiveTrue(@Param("userId") String userId, @Param("password") String password);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.userType = :userType")
    Long countByUserType(@Param("userType") UserType userType);
    
    boolean existsByEmail(String email);
    
    boolean existsByUserId(String userId);
}
