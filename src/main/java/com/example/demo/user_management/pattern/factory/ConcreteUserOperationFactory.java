package com.example.demo.user_management.pattern.factory;

import com.example.demo.user_management.entity.UserType;
import org.springframework.stereotype.Component;

@Component
public class ConcreteUserOperationFactory implements UserOperationFactory {
    
    @Override
    public UserOperation createOperation(UserType userType) {
        return switch (userType) {
            case ADMIN -> new AdminUserOperation();
            case CUSTOMER -> new CustomerUserOperation();
            case SUPERVISOR -> new SupervisorUserOperation();
            case OPERATOR -> new OperatorUserOperation();
            case ACCOUNTANT -> new AccountantUserOperation();
        };
    }
}
