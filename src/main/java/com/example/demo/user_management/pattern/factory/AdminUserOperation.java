package com.example.demo.user_management.pattern.factory;

import com.example.demo.user_management.entity.UserType;

public class AdminUserOperation implements UserOperation {
    
    @Override
    public String execute(String operation) {
        return "Admin executing: " + operation + " with full privileges";
    }
    
    @Override
    public UserType getUserType() {
        return UserType.ADMIN;
    }
}
