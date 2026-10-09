package com.example.demo.user_management.pattern.factory;

import com.example.demo.user_management.entity.UserType;

public class CustomerUserOperation implements UserOperation {
    
    @Override
    public String execute(String operation) {
        return "Customer executing: " + operation + " with limited privileges";
    }
    
    @Override
    public UserType getUserType() {
        return UserType.CUSTOMER;
    }
}
