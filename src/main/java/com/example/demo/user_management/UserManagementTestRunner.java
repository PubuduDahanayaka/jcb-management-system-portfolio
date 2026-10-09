package com.example.demo.user_management;

import com.example.demo.user_management.dto.CreateUserDTO;
import com.example.demo.user_management.dto.LoginDTO;
import com.example.demo.user_management.entity.UserType;
import com.example.demo.user_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserManagementTestRunner implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== User Management System Test ===");
        
        try {
            CreateUserDTO createUserDTO = new CreateUserDTO(
                    "John", "Doe", "john.doe@example.com", 
                    "1234567890", "password123", UserType.CUSTOMER
            );
            
            var createdUser = userService.createUser(createUserDTO);
            System.out.println("Created User: " + createdUser.getUserId() + " - " + createdUser.getFirstName() + " " + createdUser.getLastName());
            
            LoginDTO loginDTO = new LoginDTO(createdUser.getUserId(), "password123");
            var loggedInUser = userService.login(loginDTO);
            System.out.println("Login successful for: " + loggedInUser.getUserId());
            
            System.out.println("=== Test completed successfully ===");
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }
}
