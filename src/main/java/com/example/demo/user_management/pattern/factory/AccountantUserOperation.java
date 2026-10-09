package com.example.demo.user_management.pattern.factory;

import com.example.demo.user_management.entity.UserType;

public class AccountantUserOperation implements UserOperation {
    
    @Override
    public String execute(String operation) {
        return "Accountant executing: " + operation + " with financial privileges";
    }
    
    @Override
    public UserType getUserType() {
        return UserType.ACCOUNTANT;
    }
}
