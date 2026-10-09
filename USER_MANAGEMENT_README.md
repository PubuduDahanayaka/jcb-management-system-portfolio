# User Management System

A comprehensive Spring Boot application implementing a user management system with JPA, design patterns, and RESTful APIs.

## Features

- **User Entity**: Complete JPA entity with auto-generated user IDs (JCB1234 format)
- **User Types**: ADMIN, CUSTOMER, SUPERVISOR, OPERATOR, ACCOUNTANT
- **CRUD Operations**: Full Create, Read, Update, Delete functionality
- **Authentication**: Login with user ID and password
- **Design Patterns**: Factory and Decorator patterns implemented
- **Validation**: Comprehensive input validation using Bean Validation
- **Password Security**: BCrypt password encoding
- **RESTful API**: Complete REST endpoints for all operations

## User ID Format

User IDs are auto-generated in the format: `{PREFIX}{SEQUENCE}`

- **ADMIN**: ADM0001, ADM0002, etc.
- **CUSTOMER**: CUS0001, CUS0002, etc.
- **SUPERVISOR**: SUP0001, SUP0002, etc.
- **OPERATOR**: OPE0001, OPE0002, etc.
- **ACCOUNTANT**: ACC0001, ACC0002, etc.

## API Endpoints

### User Management
- `POST /api/users` - Create a new user
- `GET /api/users/{userId}` - Get user by ID
- `GET /api/users` - Get all users
- `PUT /api/users/{userId}` - Update user
- `DELETE /api/users/{userId}` - Delete user (soft delete)

### Authentication
- `POST /api/users/login` - User login

### User Type Filtering
- `GET /api/users/type/{userType}` - Get users by type

## Design Patterns Implemented

### Factory Pattern
- `UserOperationFactory` - Creates user operations based on user type
- Concrete implementations for each user type
- Provides different privilege levels for different user types

### Decorator Pattern
- `UserOperationDecorator` - Base decorator class
- `LoggingUserOperationDecorator` - Adds logging functionality
- `ValidationUserOperationDecorator` - Adds validation functionality
- Allows dynamic addition of behaviors to user operations

## Database Schema

```sql
CREATE TABLE users (
    user_id VARCHAR(10) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    contact_number VARCHAR(10) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type ENUM('ADMIN', 'CUSTOMER', 'SUPERVISOR', 'OPERATOR', 'ACCOUNTANT') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);
```

## Validation Rules

### CreateUserDTO
- First Name: Required, 2-50 characters
- Last Name: Required, 2-50 characters
- Email: Required, valid email format, unique
- Contact Number: Required, exactly 10 digits
- Password: Required, minimum 6 characters
- User Type: Required, must be valid enum value

### UpdateUserDTO
- All fields optional
- Same validation rules as CreateUserDTO when provided

### LoginDTO
- User ID: Required, 7-10 characters
- Password: Required, minimum 6 characters

## Security Features

- **Password Encoding**: BCrypt hashing for secure password storage
- **Input Validation**: Comprehensive validation using Bean Validation
- **Soft Delete**: Users are marked as inactive instead of being physically deleted
- **Error Handling**: Proper exception handling with meaningful error messages

## Usage Examples

### Create User
```json
POST /api/users
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "contactNumber": "1234567890",
    "password": "password123",
    "userType": "CUSTOMER"
}
```

### Login
```json
POST /api/users/login
{
    "userId": "CUS0001",
    "password": "password123"
}
```

### Update User
```json
PUT /api/users/CUS0001
{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@example.com"
}
```

## Error Handling

All endpoints include comprehensive error handling:
- **400 Bad Request**: Validation errors, business logic errors
- **401 Unauthorized**: Invalid login credentials
- **404 Not Found**: User not found
- **500 Internal Server Error**: Unexpected server errors

## Testing

The application includes a test runner that demonstrates:
- User creation
- User login
- Pattern usage (Factory and Decorator)

Run the application to see the test output in the console.

## Dependencies

- Spring Boot 3.5.6
- Spring Data JPA
- Spring Boot Validation
- MySQL Connector
- BCrypt for password encoding
- H2 Database (for testing)

## Configuration

The application is configured to use MySQL database. Update `application.properties` for your database configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jcbsystem?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_password
```

## Architecture

The system follows SOLID principles with:
- **Single Responsibility**: Each class has a single responsibility
- **Open/Closed**: Open for extension, closed for modification (Decorator pattern)
- **Liskov Substitution**: Proper inheritance hierarchy
- **Interface Segregation**: Focused interfaces
- **Dependency Inversion**: Dependency injection throughout

The codebase is highly loosely coupled with clear separation of concerns between entities, DTOs, repositories, services, and controllers.
