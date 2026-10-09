package com.example.demo.machine_management.service;

import com.example.demo.machine_management.dto.CreateMachineDTO;
import com.example.demo.machine_management.dto.MachineResponseDTO;
import com.example.demo.machine_management.dto.UpdateMachineDTO;
import com.example.demo.machine_management.entity.FuelType;
import com.example.demo.machine_management.entity.Machine;
import com.example.demo.machine_management.entity.MachineType;
import com.example.demo.machine_management.pattern.decorator.LoggingMachineOperationDecorator;
import com.example.demo.machine_management.pattern.decorator.SafetyMachineOperationDecorator;
import com.example.demo.machine_management.pattern.decorator.ValidationMachineOperationDecorator;
import com.example.demo.machine_management.pattern.factory.MachineOperation;
import com.example.demo.machine_management.pattern.factory.MachineOperationFactory;
import com.example.demo.machine_management.repository.MachineRepository;
import com.example.demo.machine_management.util.MachineIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MachineService {
    
    @Autowired
    private MachineRepository machineRepository;
    
    @Autowired
    private MachineOperationFactory machineOperationFactory;
    
    @Autowired
    private MachineIdGenerator machineIdGenerator;
    
    public MachineResponseDTO createMachine(CreateMachineDTO createMachineDTO) {
        try {
            Machine machine = new Machine();
            machine.setMachineType(createMachineDTO.getMachineType());
            machine.setFuelType(createMachineDTO.getFuelType());
            machine.setMaxFuelCapacity(createMachineDTO.getMaxFuelCapacity());
            machine.setCurrentFuelLevel(createMachineDTO.getMaxFuelCapacity());
            
            String generatedMachineId = machineIdGenerator.generateMachineId(createMachineDTO.getMachineType());
            machine.setMachineId(generatedMachineId);
            
            Machine savedMachine = machineRepository.save(machine);
            
            MachineOperation operation = machineOperationFactory.createOperation(savedMachine.getMachineType());
            operation = new ValidationMachineOperationDecorator(operation);
            operation = new SafetyMachineOperationDecorator(operation);
            operation = new LoggingMachineOperationDecorator(operation);
            operation.execute("CREATE_MACHINE");
            
            return convertToResponseDTO(savedMachine);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create machine: " + e.getMessage(), e);
        }
    }
    
    public MachineResponseDTO getMachineById(String machineId) {
        try {
            Optional<Machine> machine = machineRepository.findByMachineIdAndIsActiveTrue(machineId);
            if (machine.isEmpty()) {
                throw new RuntimeException("Machine not found with ID: " + machineId);
            }
            
            MachineOperation operation = machineOperationFactory.createOperation(machine.get().getMachineType());
            operation = new ValidationMachineOperationDecorator(operation);
            operation = new SafetyMachineOperationDecorator(operation);
            operation = new LoggingMachineOperationDecorator(operation);
            operation.execute("GET_MACHINE");
            
            return convertToResponseDTO(machine.get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get machine: " + e.getMessage(), e);
        }
    }
    
    public List<MachineResponseDTO> getAllMachines() {
        try {
            List<Machine> machines = machineRepository.findByIsActiveTrue();
            
            MachineOperation operation = machineOperationFactory.createOperation(MachineType.EXCAVATOR);
            operation = new ValidationMachineOperationDecorator(operation);
            operation = new SafetyMachineOperationDecorator(operation);
            operation = new LoggingMachineOperationDecorator(operation);
            operation.execute("GET_ALL_MACHINES");
            
            return machines.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all machines: " + e.getMessage(), e);
        }
    }
    
    public MachineResponseDTO updateMachine(String machineId, UpdateMachineDTO updateMachineDTO) {
        try {
            Optional<Machine> machineOptional = machineRepository.findByMachineIdAndIsActiveTrue(machineId);
            if (machineOptional.isEmpty()) {
                throw new RuntimeException("Machine not found with ID: " + machineId);
            }
            
            Machine machine = machineOptional.get();
            
            if (updateMachineDTO.getMachineType() != null) {
                machine.setMachineType(updateMachineDTO.getMachineType());
            }
            if (updateMachineDTO.getFuelType() != null) {
                machine.setFuelType(updateMachineDTO.getFuelType());
            }
            if (updateMachineDTO.getMaxFuelCapacity() != null) {
                machine.setMaxFuelCapacity(updateMachineDTO.getMaxFuelCapacity());
            }
            if (updateMachineDTO.getCurrentFuelLevel() != null) {
                machine.setCurrentFuelLevel(updateMachineDTO.getCurrentFuelLevel());
            }
            if (updateMachineDTO.getOperatingHours() != null) {
                machine.setOperatingHours(updateMachineDTO.getOperatingHours());
            }
            if (updateMachineDTO.getLastMaintenanceDate() != null) {
                machine.setLastMaintenanceDate(updateMachineDTO.getLastMaintenanceDate());
            }
            if (updateMachineDTO.getNextMaintenanceDate() != null) {
                machine.setNextMaintenanceDate(updateMachineDTO.getNextMaintenanceDate());
            }
            if (updateMachineDTO.getIsActive() != null) {
                machine.setIsActive(updateMachineDTO.getIsActive());
            }
            
            Machine updatedMachine = machineRepository.save(machine);
            
            MachineOperation operation = machineOperationFactory.createOperation(updatedMachine.getMachineType());
            operation = new ValidationMachineOperationDecorator(operation);
            operation = new SafetyMachineOperationDecorator(operation);
            operation = new LoggingMachineOperationDecorator(operation);
            operation.execute("UPDATE_MACHINE");
            
            return convertToResponseDTO(updatedMachine);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update machine: " + e.getMessage(), e);
        }
    }
    
    public void deleteMachine(String machineId) {
        try {
            Optional<Machine> machineOptional = machineRepository.findByMachineIdAndIsActiveTrue(machineId);
            if (machineOptional.isEmpty()) {
                throw new RuntimeException("Machine not found with ID: " + machineId);
            }
            
            Machine machine = machineOptional.get();
            machine.setIsActive(false);
            machineRepository.save(machine);
            
            MachineOperation operation = machineOperationFactory.createOperation(machine.getMachineType());
            operation = new ValidationMachineOperationDecorator(operation);
            operation = new SafetyMachineOperationDecorator(operation);
            operation = new LoggingMachineOperationDecorator(operation);
            operation.execute("DELETE_MACHINE");
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete machine: " + e.getMessage(), e);
        }
    }
    
    public List<MachineResponseDTO> getMachinesByType(MachineType machineType) {
        try {
            List<Machine> machines = machineRepository.findByMachineType(machineType);
            
            MachineOperation operation = machineOperationFactory.createOperation(machineType);
            operation = new ValidationMachineOperationDecorator(operation);
            operation = new SafetyMachineOperationDecorator(operation);
            operation = new LoggingMachineOperationDecorator(operation);
            operation.execute("GET_MACHINES_BY_TYPE");
            
            return machines.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get machines by type: " + e.getMessage(), e);
        }
    }
    
    public List<MachineResponseDTO> getMachinesByFuelType(FuelType fuelType) {
        try {
            List<Machine> machines = machineRepository.findByFuelType(fuelType);
            
            MachineOperation operation = machineOperationFactory.createOperation(MachineType.EXCAVATOR);
            operation = new ValidationMachineOperationDecorator(operation);
            operation = new SafetyMachineOperationDecorator(operation);
            operation = new LoggingMachineOperationDecorator(operation);
            operation.execute("GET_MACHINES_BY_FUEL_TYPE");
            
            return machines.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get machines by fuel type: " + e.getMessage(), e);
        }
    }
    
    public List<MachineResponseDTO> getMachinesWithLowFuel(Double threshold) {
        try {
            List<Machine> machines = machineRepository.findMachinesWithLowFuel(threshold);
            
            MachineOperation operation = machineOperationFactory.createOperation(MachineType.EXCAVATOR);
            operation = new ValidationMachineOperationDecorator(operation);
            operation = new SafetyMachineOperationDecorator(operation);
            operation = new LoggingMachineOperationDecorator(operation);
            operation.execute("GET_MACHINES_WITH_LOW_FUEL");
            
            return machines.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get machines with low fuel: " + e.getMessage(), e);
        }
    }
    
    public List<MachineResponseDTO> getMachinesDueForMaintenance() {
        try {
            List<Machine> machines = machineRepository.findMachinesDueForMaintenance(LocalDateTime.now());
            
            MachineOperation operation = machineOperationFactory.createOperation(MachineType.EXCAVATOR);
            operation = new ValidationMachineOperationDecorator(operation);
            operation = new SafetyMachineOperationDecorator(operation);
            operation = new LoggingMachineOperationDecorator(operation);
            operation.execute("GET_MACHINES_DUE_FOR_MAINTENANCE");
            
            return machines.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get machines due for maintenance: " + e.getMessage(), e);
        }
    }
    
    public MachineResponseDTO refuelMachine(String machineId, Double fuelAmount) {
        try {
            Optional<Machine> machineOptional = machineRepository.findByMachineIdAndIsActiveTrue(machineId);
            if (machineOptional.isEmpty()) {
                throw new RuntimeException("Machine not found with ID: " + machineId);
            }
            
            Machine machine = machineOptional.get();
            Double newFuelLevel = machine.getCurrentFuelLevel() + fuelAmount;
            
            if (newFuelLevel > machine.getMaxFuelCapacity()) {
                machine.setCurrentFuelLevel(machine.getMaxFuelCapacity());
            } else {
                machine.setCurrentFuelLevel(newFuelLevel);
            }
            
            Machine updatedMachine = machineRepository.save(machine);
            
            MachineOperation operation = machineOperationFactory.createOperation(updatedMachine.getMachineType());
            operation = new ValidationMachineOperationDecorator(operation);
            operation = new SafetyMachineOperationDecorator(operation);
            operation = new LoggingMachineOperationDecorator(operation);
            operation.execute("REFUEL_MACHINE");
            
            return convertToResponseDTO(updatedMachine);
        } catch (Exception e) {
            throw new RuntimeException("Failed to refuel machine: " + e.getMessage(), e);
        }
    }
    
    private MachineResponseDTO convertToResponseDTO(Machine machine) {
        return new MachineResponseDTO(
                machine.getMachineId(),
                machine.getMachineType(),
                machine.getFuelType(),
                machine.getCreatedAt(),
                machine.getUpdatedAt(),
                machine.getIsActive(),
                machine.getCurrentFuelLevel(),
                machine.getMaxFuelCapacity(),
                machine.getOperatingHours(),
                machine.getLastMaintenanceDate(),
                machine.getNextMaintenanceDate()
        );
    }
}
