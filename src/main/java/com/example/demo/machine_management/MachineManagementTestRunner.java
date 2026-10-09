package com.example.demo.machine_management;

import com.example.demo.machine_management.dto.CreateMachineDTO;
import com.example.demo.machine_management.entity.FuelType;
import com.example.demo.machine_management.entity.MachineType;
import com.example.demo.machine_management.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MachineManagementTestRunner implements CommandLineRunner {
    
    @Autowired
    private MachineService machineService;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Machine Management System Test ===");
        
        try {
            CreateMachineDTO createMachineDTO = new CreateMachineDTO(
                    MachineType.EXCAVATOR, FuelType.DIESEL, 100.0
            );
            
            var createdMachine = machineService.createMachine(createMachineDTO);
            System.out.println("Created Machine: " + createdMachine.getMachineId() + " - " + createdMachine.getMachineType());
            
            var retrievedMachine = machineService.getMachineById(createdMachine.getMachineId());
            System.out.println("Retrieved Machine: " + retrievedMachine.getMachineId() + " - Fuel Level: " + retrievedMachine.getCurrentFuelLevel());
            
            var refueledMachine = machineService.refuelMachine(createdMachine.getMachineId(), 50.0);
            System.out.println("Refueled Machine: " + refueledMachine.getMachineId() + " - New Fuel Level: " + refueledMachine.getCurrentFuelLevel());
            
            System.out.println("=== Machine Management Test completed successfully ===");
        } catch (Exception e) {
            System.err.println("Machine Management Test failed: " + e.getMessage());
        }
    }
}
