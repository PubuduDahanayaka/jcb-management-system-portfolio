package com.example.demo.user_management.pattern.factory;

import com.example.demo.user_management.entity.UserType;

public class SupervisorUserOperation implements UserOperation {
    
    @Override
    public String execute(String operation) {
        return "Supervisor executing: " + operation + " with supervisory privileges";
    }
    
    @Override
    public UserType getUserType() {
        return UserType.SUPERVISOR;
    }
}
