package com.example.demo.user_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginDTO {
    
    @NotBlank(message = "User ID is required")
    @Size(min = 7, max = 10, message = "User ID must be between 7 and 10 characters")
    private String userId;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    public LoginDTO() {}
    
    public LoginDTO(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
