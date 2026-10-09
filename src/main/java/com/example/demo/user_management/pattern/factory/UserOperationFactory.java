package com.example.demo.user_management.pattern.factory;

import com.example.demo.user_management.entity.UserType;

public interface UserOperationFactory {
    UserOperation createOperation(UserType userType);
}
