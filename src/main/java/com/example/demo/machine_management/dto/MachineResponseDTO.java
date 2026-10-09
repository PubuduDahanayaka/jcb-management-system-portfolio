package com.example.demo.machine_management.dto;

import com.example.demo.machine_management.entity.FuelType;
import com.example.demo.machine_management.entity.MachineType;

import java.time.LocalDateTime;

public class MachineResponseDTO {
    
    private String machineId;
    private MachineType machineType;
    private FuelType fuelType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private Double currentFuelLevel;
    private Double maxFuelCapacity;
    private Double operatingHours;
    private LocalDateTime lastMaintenanceDate;
    private LocalDateTime nextMaintenanceDate;
    
    public MachineResponseDTO() {}
    
    public MachineResponseDTO(String machineId, MachineType machineType, FuelType fuelType, 
                             LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isActive,
                             Double currentFuelLevel, Double maxFuelCapacity, Double operatingHours,
                             LocalDateTime lastMaintenanceDate, LocalDateTime nextMaintenanceDate) {
        this.machineId = machineId;
        this.machineType = machineType;
        this.fuelType = fuelType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.currentFuelLevel = currentFuelLevel;
        this.maxFuelCapacity = maxFuelCapacity;
        this.operatingHours = operatingHours;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.nextMaintenanceDate = nextMaintenanceDate;
    }
    
    public String getMachineId() {
        return machineId;
    }
    
    public void setMachineId(String machineId) {
        this.machineId = machineId;
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Double getCurrentFuelLevel() {
        return currentFuelLevel;
    }
    
    public void setCurrentFuelLevel(Double currentFuelLevel) {
        this.currentFuelLevel = currentFuelLevel;
    }
    
    public Double getMaxFuelCapacity() {
        return maxFuelCapacity;
    }
    
    public void setMaxFuelCapacity(Double maxFuelCapacity) {
        this.maxFuelCapacity = maxFuelCapacity;
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
}
