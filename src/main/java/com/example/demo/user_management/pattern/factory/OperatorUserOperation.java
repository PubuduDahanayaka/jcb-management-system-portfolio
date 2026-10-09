package com.example.demo.user_management.pattern.factory;

import com.example.demo.user_management.entity.UserType;

public class OperatorUserOperation implements UserOperation {
    
    @Override
    public String execute(String operation) {
        return "Operator executing: " + operation + " with operational privileges";
    }
    
    @Override
    public UserType getUserType() {
        return UserType.OPERATOR;
    }
}
