package com.example.demo.machine_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "machines")
public class Machine {
    
    @Id
    @Column(name = "machine_id", unique = true, nullable = false, length = 10)
    private String machineId;
    
    @NotNull(message = "Machine type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "machine_type", nullable = false)
    private MachineType machineType;
    
    @NotNull(message = "Fuel type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "current_fuel_level")
    private Double currentFuelLevel;
    
    @Column(name = "max_fuel_capacity")
    private Double maxFuelCapacity;
    
    @Column(name = "operating_hours")
    private Double operatingHours = 0.0;
    
    @Column(name = "last_maintenance_date")
    private LocalDateTime lastMaintenanceDate;
    
    @Column(name = "next_maintenance_date")
    private LocalDateTime nextMaintenanceDate;
    
    public Machine() {}
    
    public Machine(MachineType machineType, FuelType fuelType, Double maxFuelCapacity) {
        this.machineType = machineType;
        this.fuelType = fuelType;
        this.maxFuelCapacity = maxFuelCapacity;
        this.currentFuelLevel = maxFuelCapacity;
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
