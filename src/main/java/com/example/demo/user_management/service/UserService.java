package com.example.demo.user_management.service;

import com.example.demo.user_management.dto.CreateUserDTO;
import com.example.demo.user_management.dto.LoginDTO;
import com.example.demo.user_management.dto.UpdateUserDTO;
import com.example.demo.user_management.dto.UserResponseDTO;
import com.example.demo.user_management.entity.User;
import com.example.demo.user_management.entity.UserType;
import com.example.demo.user_management.pattern.decorator.LoggingUserOperationDecorator;
import com.example.demo.user_management.pattern.decorator.ValidationUserOperationDecorator;
import com.example.demo.user_management.pattern.factory.UserOperation;
import com.example.demo.user_management.pattern.factory.UserOperationFactory;
import com.example.demo.user_management.repository.UserRepository;
import com.example.demo.user_management.util.PasswordEncoder;
import com.example.demo.user_management.util.UserIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserOperationFactory userOperationFactory;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserIdGenerator userIdGenerator;
    
    public UserResponseDTO createUser(CreateUserDTO createUserDTO) {
        try {
            if (userRepository.existsByEmail(createUserDTO.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
            
            User user = new User();
            user.setFirstName(createUserDTO.getFirstName());
            user.setLastName(createUserDTO.getLastName());
            user.setEmail(createUserDTO.getEmail());
            user.setContactNumber(createUserDTO.getContactNumber());
            user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
            user.setUserType(createUserDTO.getUserType());
            
            String generatedUserId = userIdGenerator.generateUserId(createUserDTO.getUserType());
            user.setUserId(generatedUserId);
            
            User savedUser = userRepository.save(user);
            
            UserOperation operation = userOperationFactory.createOperation(savedUser.getUserType());
            operation = new ValidationUserOperationDecorator(operation);
            operation = new LoggingUserOperationDecorator(operation);
            operation.execute("CREATE_USER");
            
            return convertToResponseDTO(savedUser);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user: " + e.getMessage(), e);
        }
    }
    
    public UserResponseDTO getUserById(String userId) {
        try {
            Optional<User> user = userRepository.findByUserIdAndIsActiveTrue(userId);
            if (user.isEmpty()) {
                throw new RuntimeException("User not found with ID: " + userId);
            }
            
            UserOperation operation = userOperationFactory.createOperation(user.get().getUserType());
            operation = new ValidationUserOperationDecorator(operation);
            operation = new LoggingUserOperationDecorator(operation);
            operation.execute("GET_USER");
            
            return convertToResponseDTO(user.get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user: " + e.getMessage(), e);
        }
    }
    
    public List<UserResponseDTO> getAllUsers() {
        try {
            List<User> users = userRepository.findByIsActiveTrue();
            
            UserOperation operation = userOperationFactory.createOperation(UserType.ADMIN);
            operation = new ValidationUserOperationDecorator(operation);
            operation = new LoggingUserOperationDecorator(operation);
            operation.execute("GET_ALL_USERS");
            
            return users.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all users: " + e.getMessage(), e);
        }
    }
    
    public UserResponseDTO updateUser(String userId, UpdateUserDTO updateUserDTO) {
        try {
            Optional<User> userOptional = userRepository.findByUserIdAndIsActiveTrue(userId);
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found with ID: " + userId);
            }
            
            User user = userOptional.get();
            
            if (updateUserDTO.getFirstName() != null) {
                user.setFirstName(updateUserDTO.getFirstName());
            }
            if (updateUserDTO.getLastName() != null) {
                user.setLastName(updateUserDTO.getLastName());
            }
            if (updateUserDTO.getEmail() != null && !updateUserDTO.getEmail().equals(user.getEmail())) {
                if (userRepository.existsByEmail(updateUserDTO.getEmail())) {
                    throw new RuntimeException("Email already exists");
                }
                user.setEmail(updateUserDTO.getEmail());
            }
            if (updateUserDTO.getContactNumber() != null) {
                user.setContactNumber(updateUserDTO.getContactNumber());
            }
            if (updateUserDTO.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
            }
            if (updateUserDTO.getUserType() != null) {
                user.setUserType(updateUserDTO.getUserType());
            }
            if (updateUserDTO.getIsActive() != null) {
                user.setIsActive(updateUserDTO.getIsActive());
            }
            
            User updatedUser = userRepository.save(user);
            
            UserOperation operation = userOperationFactory.createOperation(updatedUser.getUserType());
            operation = new ValidationUserOperationDecorator(operation);
            operation = new LoggingUserOperationDecorator(operation);
            operation.execute("UPDATE_USER");
            
            return convertToResponseDTO(updatedUser);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user: " + e.getMessage(), e);
        }
    }
    
    public void deleteUser(String userId) {
        try {
            Optional<User> userOptional = userRepository.findByUserIdAndIsActiveTrue(userId);
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found with ID: " + userId);
            }
            
            User user = userOptional.get();
            user.setIsActive(false);
            userRepository.save(user);
            
            UserOperation operation = userOperationFactory.createOperation(user.getUserType());
            operation = new ValidationUserOperationDecorator(operation);
            operation = new LoggingUserOperationDecorator(operation);
            operation.execute("DELETE_USER");
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage(), e);
        }
    }
    
    public UserResponseDTO login(LoginDTO loginDTO) {
        try {
            Optional<User> userOptional = userRepository.findByUserIdAndIsActiveTrue(loginDTO.getUserId());
            if (userOptional.isEmpty()) {
                throw new RuntimeException("Invalid credentials");
            }
            
            User user = userOptional.get();
            if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }
            
            UserOperation operation = userOperationFactory.createOperation(user.getUserType());
            operation = new ValidationUserOperationDecorator(operation);
            operation = new LoggingUserOperationDecorator(operation);
            operation.execute("LOGIN");
            
            return convertToResponseDTO(user);
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        }
    }
    
    public List<UserResponseDTO> getUsersByType(UserType userType) {
        try {
            List<User> users = userRepository.findByUserType(userType);
            
            UserOperation operation = userOperationFactory.createOperation(userType);
            operation = new ValidationUserOperationDecorator(operation);
            operation = new LoggingUserOperationDecorator(operation);
            operation.execute("GET_USERS_BY_TYPE");
            
            return users.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get users by type: " + e.getMessage(), e);
        }
    }
    
    private UserResponseDTO convertToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getContactNumber(),
                user.getUserType(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getIsActive()
        );
    }
}
