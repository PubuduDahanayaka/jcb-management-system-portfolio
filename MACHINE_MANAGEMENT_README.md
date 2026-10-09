# Machine Management System

A comprehensive Spring Boot application implementing a machine management system for JCB operations with JPA, design patterns, and RESTful APIs.

## Features

- **Machine Entity**: Complete JPA entity with auto-generated machine IDs (MCH1234 format)
- **Machine Types**: EXCAVATOR, LOADER, BULLDOZER, CRANE, FORKLIFT, COMPACTOR, GRADER, BACKHOE, SKID_STEER, DUMP_TRUCK
- **Fuel Types**: DIESEL, PETROL, ELECTRIC, HYBRID, LPG, CNG
- **CRUD Operations**: Full Create, Read, Update, Delete functionality
- **Design Patterns**: Factory and Decorator patterns implemented
- **Validation**: Comprehensive input validation using Bean Validation
- **RESTful API**: Complete REST endpoints for all operations
- **Advanced Features**: Fuel management, maintenance tracking, operating hours

## Machine ID Format

Machine IDs are auto-generated in the format: `{PREFIX}{SEQUENCE}`

- **EXCAVATOR**: EXC0001, EXC0002, etc.
- **LOADER**: LOD0001, LOD0002, etc.
- **BULLDOZER**: BUL0001, BUL0002, etc.
- **CRANE**: CRA0001, CRA0002, etc.
- **FORKLIFT**: FOR0001, FOR0002, etc.
- **COMPACTOR**: COM0001, COM0002, etc.
- **GRADER**: GRA0001, GRA0002, etc.
- **BACKHOE**: BAC0001, BAC0002, etc.
- **SKID_STEER**: SKI0001, SKI0002, etc.
- **DUMP_TRUCK**: DUM0001, DUM0002, etc.

## API Endpoints

### Machine Management
- `POST /api/machines` - Create a new machine
- `GET /api/machines/{machineId}` - Get machine by ID
- `GET /api/machines` - Get all machines
- `PUT /api/machines/{machineId}` - Update machine
- `DELETE /api/machines/{machineId}` - Delete machine (soft delete)

### Machine Filtering
- `GET /api/machines/type/{machineType}` - Get machines by type
- `GET /api/machines/fuel-type/{fuelType}` - Get machines by fuel type
- `GET /api/machines/low-fuel?threshold=10.0` - Get machines with low fuel
- `GET /api/machines/maintenance-due` - Get machines due for maintenance

### Machine Operations
- `POST /api/machines/{machineId}/refuel?fuelAmount=50.0` - Refuel machine

## Design Patterns Implemented

### Factory Pattern
- `MachineOperationFactory` - Creates machine operations based on machine type
- Concrete implementations for each machine type
- Provides different operational capabilities for different machine types

### Decorator Pattern
- `MachineOperationDecorator` - Base decorator class
- `LoggingMachineOperationDecorator` - Adds logging functionality
- `ValidationMachineOperationDecorator` - Adds validation functionality
- `SafetyMachineOperationDecorator` - Adds safety checks
- Allows dynamic addition of behaviors to machine operations

## Database Schema

```sql
CREATE TABLE machines (
    machine_id VARCHAR(10) PRIMARY KEY,
    machine_type ENUM('EXCAVATOR', 'LOADER', 'BULLDOZER', 'CRANE', 'FORKLIFT', 'COMPACTOR', 'GRADER', 'BACKHOE', 'SKID_STEER', 'DUMP_TRUCK') NOT NULL,
    fuel_type ENUM('DIESEL', 'PETROL', 'ELECTRIC', 'HYBRID', 'LPG', 'CNG') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    current_fuel_level DOUBLE,
    max_fuel_capacity DOUBLE,
    operating_hours DOUBLE DEFAULT 0.0,
    last_maintenance_date TIMESTAMP,
    next_maintenance_date TIMESTAMP
);
```

## Validation Rules

### CreateMachineDTO
- Machine Type: Required, must be valid enum value
- Fuel Type: Required, must be valid enum value
- Max Fuel Capacity: Required, must be positive

### UpdateMachineDTO
- All fields optional
- Same validation rules as CreateMachineDTO when provided
- Operating Hours: Must be positive
- Fuel Level: Must be positive

## Security Features

- **Input Validation**: Comprehensive validation using Bean Validation
- **Soft Delete**: Machines are marked as inactive instead of being physically deleted
- **Error Handling**: Proper exception handling with meaningful error messages
- **Safety Checks**: Built-in safety decorators for machine operations

## Usage Examples

### Create Machine
```json
POST /api/machines
{
    "machineType": "EXCAVATOR",
    "fuelType": "DIESEL",
    "maxFuelCapacity": 100.0
}
```

### Update Machine
```json
PUT /api/machines/EXC0001
{
    "currentFuelLevel": 75.0,
    "operatingHours": 150.5,
    "nextMaintenanceDate": "2024-02-15T10:00:00"
}
```

### Refuel Machine
```
POST /api/machines/EXC0001/refuel?fuelAmount=25.0
```

### Get Machines by Type
```
GET /api/machines/type/EXCAVATOR
```

### Get Machines with Low Fuel
```
GET /api/machines/low-fuel?threshold=20.0
```

## Error Handling

All endpoints include comprehensive error handling:
- **400 Bad Request**: Validation errors, business logic errors
- **404 Not Found**: Machine not found
- **500 Internal Server Error**: Unexpected server errors

## Testing

The application includes a test runner that demonstrates:
- Machine creation
- Machine retrieval
- Machine refueling
- Pattern usage (Factory and Decorator)

Run the application to see the test output in the console.

## Dependencies

- Spring Boot 3.5.6
- Spring Data JPA
- Spring Boot Validation
- MySQL Connector
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

## Machine Operations

Each machine type has specific operational capabilities:

- **Excavator**: Digging operations
- **Loader**: Material handling
- **Bulldozer**: Earthmoving
- **Crane**: Lifting operations
- **Forklift**: Warehouse operations
- **Compactor**: Soil compaction
- **Grader**: Surface grading
- **Backhoe**: Excavation and loading
- **Skid Steer**: Versatile operations
- **Dump Truck**: Material transport

## Maintenance Features

- Track operating hours
- Schedule maintenance dates
- Monitor fuel levels
- Identify machines due for maintenance
- Low fuel alerts

This system provides a complete solution for managing JCB machines with proper tracking, maintenance scheduling, and operational monitoring.
