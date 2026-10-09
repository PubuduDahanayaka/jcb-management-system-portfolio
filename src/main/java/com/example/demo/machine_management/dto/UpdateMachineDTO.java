package com.example.demo.machine_management.dto;

import com.example.demo.machine_management.entity.FuelType;
import com.example.demo.machine_management.entity.MachineType;
import jakarta.validation.constraints.DecimalMin;

import java.time.LocalDateTime;

public class UpdateMachineDTO {
    
    private MachineType machineType;
    
    private FuelType fuelType;
    
    @DecimalMin(value = "0.0", message = "Max fuel capacity must be positive")
    private Double maxFuelCapacity;
    
    @DecimalMin(value = "0.0", message = "Current fuel level must be positive")
    private Double currentFuelLevel;
    
    @DecimalMin(value = "0.0", message = "Operating hours must be positive")
    private Double operatingHours;
    
    private LocalDateTime lastMaintenanceDate;
    
    private LocalDateTime nextMaintenanceDate;
    
    private Boolean isActive;
    
    public UpdateMachineDTO() {}
    
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
    
    public Double getCurrentFuelLevel() {
        return currentFuelLevel;
    }
    
    public void setCurrentFuelLevel(Double currentFuelLevel) {
        this.currentFuelLevel = currentFuelLevel;
    }
    
    public Double getOperatingHours() {
        return operatingHours;
    }
    
    public void setOperatingHours(Double operatingHours) {
        this.operatingHours = operatingHours;
    }
    
    public LocalDateTime getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }
    
    public void setLastMaintenanceDate(LocalDateTime lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }
    
    public LocalDateTime getNextMaintenanceDate() {
        return nextMaintenanceDate;
    }
    
    public void setNextMaintenanceDate(LocalDateTime nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
