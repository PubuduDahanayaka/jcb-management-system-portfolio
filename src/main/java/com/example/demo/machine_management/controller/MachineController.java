package com.example.demo.machine_management.controller;

import com.example.demo.machine_management.dto.CreateMachineDTO;
import com.example.demo.machine_management.dto.MachineResponseDTO;
import com.example.demo.machine_management.dto.UpdateMachineDTO;
import com.example.demo.machine_management.entity.FuelType;
import com.example.demo.machine_management.entity.MachineType;
import com.example.demo.machine_management.service.MachineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
@CrossOrigin(origins = "*")
public class MachineController {
    
    @Autowired
    private MachineService machineService;
    
    @PostMapping
    public ResponseEntity<?> createMachine(@Valid @RequestBody CreateMachineDTO createMachineDTO) {
        try {
            MachineResponseDTO machine = machineService.createMachine(createMachineDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(machine);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/{machineId}")
    public ResponseEntity<?> getMachineById(@PathVariable String machineId) {
        try {
            MachineResponseDTO machine = machineService.getMachineById(machineId);
            return ResponseEntity.ok(machine);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllMachines() {
        try {
            List<MachineResponseDTO> machines = machineService.getAllMachines();
            return ResponseEntity.ok(machines);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{machineId}")
    public ResponseEntity<?> updateMachine(@PathVariable String machineId, 
                                           @Valid @RequestBody UpdateMachineDTO updateMachineDTO) {
        try {
            MachineResponseDTO machine = machineService.updateMachine(machineId, updateMachineDTO);
            return ResponseEntity.ok(machine);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{machineId}")
    public ResponseEntity<?> deleteMachine(@PathVariable String machineId) {
        try {
            machineService.deleteMachine(machineId);
            return ResponseEntity.ok("Machine deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/type/{machineType}")
    public ResponseEntity<?> getMachinesByType(@PathVariable MachineType machineType) {
        try {
            List<MachineResponseDTO> machines = machineService.getMachinesByType(machineType);
            return ResponseEntity.ok(machines);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/fuel-type/{fuelType}")
    public ResponseEntity<?> getMachinesByFuelType(@PathVariable FuelType fuelType) {
        try {
            List<MachineResponseDTO> machines = machineService.getMachinesByFuelType(fuelType);
            return ResponseEntity.ok(machines);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/low-fuel")
    public ResponseEntity<?> getMachinesWithLowFuel(@RequestParam(defaultValue = "10.0") Double threshold) {
        try {
            List<MachineResponseDTO> machines = machineService.getMachinesWithLowFuel(threshold);
            return ResponseEntity.ok(machines);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/maintenance-due")
    public ResponseEntity<?> getMachinesDueForMaintenance() {
        try {
            List<MachineResponseDTO> machines = machineService.getMachinesDueForMaintenance();
            return ResponseEntity.ok(machines);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @PostMapping("/{machineId}/refuel")
    public ResponseEntity<?> refuelMachine(@PathVariable String machineId, 
                                          @RequestParam Double fuelAmount) {
        try {
            MachineResponseDTO machine = machineService.refuelMachine(machineId, fuelAmount);
            return ResponseEntity.ok(machine);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
}
