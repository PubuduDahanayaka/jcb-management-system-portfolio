package com.example.demo.machine_management.dto;

import com.example.demo.machine_management.entity.FuelType;
import com.example.demo.machine_management.entity.MachineType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class CreateMachineDTO {
    
    @NotNull(message = "Machine type is required")
    private MachineType machineType;
    
    @NotNull(message = "Fuel type is required")
    private FuelType fuelType;
    
    @DecimalMin(value = "0.0", message = "Max fuel capacity must be positive")
    private Double maxFuelCapacity;
    
    public CreateMachineDTO() {}
    
    public CreateMachineDTO(MachineType machineType, FuelType fuelType, Double maxFuelCapacity) {
        this.machineType = machineType;
        this.fuelType = fuelType;
        this.maxFuelCapacity = maxFuelCapacity;
    }
    
    public MachineType getMachineType() {
        return machineType;
    }
    
    public void setMachineType(MachineType machineType) {
        this.machineType = machineType;
    }
    
    public FuelType getFuelType() {
        return fuelType;
    }
    
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
    
    public Double getMaxFuelCapacity() {
        return maxFuelCapacity;
    }
    
    public void setMaxFuelCapacity(Double maxFuelCapacity) {
        this.maxFuelCapacity = maxFuelCapacity;
    }
}
