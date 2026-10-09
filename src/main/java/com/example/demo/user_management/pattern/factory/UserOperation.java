package com.example.demo.user_management.pattern.factory;

import com.example.demo.user_management.entity.UserType;

public interface UserOperation {
    String execute(String operation);
    UserType getUserType();
}
