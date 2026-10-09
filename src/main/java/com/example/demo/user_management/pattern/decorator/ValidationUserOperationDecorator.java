package com.example.demo.user_management.pattern.decorator;

import com.example.demo.user_management.pattern.factory.UserOperation;

public class ValidationUserOperationDecorator extends UserOperationDecorator {
    
    public ValidationUserOperationDecorator(UserOperation userOperation) {
        super(userOperation);
    }
    
    @Override
    public String execute(String operation) {
        if (operation == null || operation.trim().isEmpty()) {
            throw new IllegalArgumentException("Operation cannot be null or empty");
        }
        String result = super.execute(operation);
        return result + " [VALIDATED]";
    }
}
