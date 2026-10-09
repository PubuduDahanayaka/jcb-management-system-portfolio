package com.example.demo.user_management.pattern.decorator;

import com.example.demo.user_management.pattern.factory.UserOperation;

public class LoggingUserOperationDecorator extends UserOperationDecorator {
    
    public LoggingUserOperationDecorator(UserOperation userOperation) {
        super(userOperation);
    }
    
    @Override
    public String execute(String operation) {
        String result = super.execute(operation);
        System.out.println("Logging: " + getUserType() + " executed operation: " + operation);
        return result + " [LOGGED]";
    }
}
