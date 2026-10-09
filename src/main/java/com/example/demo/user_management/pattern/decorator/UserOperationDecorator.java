package com.example.demo.user_management.pattern.decorator;

import com.example.demo.user_management.pattern.factory.UserOperation;

public abstract class UserOperationDecorator implements UserOperation {
    
    protected UserOperation userOperation;
    
    public UserOperationDecorator(UserOperation userOperation) {
        this.userOperation = userOperation;
    }
    
    @Override
    public String execute(String operation) {
        return userOperation.execute(operation);
    }
    
    @Override
    public com.example.demo.user_management.entity.UserType getUserType() {
        return userOperation.getUserType();
    }
}
