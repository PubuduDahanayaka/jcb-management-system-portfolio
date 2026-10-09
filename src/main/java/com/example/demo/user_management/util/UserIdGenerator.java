package com.example.demo.user_management.util;

import com.example.demo.user_management.entity.UserType;
import com.example.demo.user_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserIdGenerator {
    
    @Autowired
    private UserRepository userRepository;
    
    public String generateUserId(UserType userType) {
        String prefix = getPrefixForUserType(userType);
        Long count = userRepository.countByUserType(userType);
        String sequence = String.format("%04d", count + 1);
        return prefix + sequence;
    }
    
    private String getPrefixForUserType(UserType userType) {
        return switch (userType) {
            case ADMIN -> "ADM";
            case CUSTOMER -> "CUS";
            case SUPERVISOR -> "SUP";
            case OPERATOR -> "OPE";
            case ACCOUNTANT -> "ACC";
        };
    }
}
