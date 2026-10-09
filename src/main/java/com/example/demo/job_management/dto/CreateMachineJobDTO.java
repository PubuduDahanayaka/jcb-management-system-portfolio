package com.example.demo.job_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateMachineJobDTO {
    
    @NotNull(message = "Assigned user ID is required")
    private String assignedUserId;
    
    @NotNull(message = "Machine ID is required")
    private String machineId;
    
    @NotBlank(message = "Venue is required")
    @Size(min = 3, max = 200, message = "Venue must be between 3 and 200 characters")
    private String venue;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "Start time is required")
    private LocalTime startTime;
    
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    
    @NotNull(message = "End time is required")
    private LocalTime endTime;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    private Double estimatedDurationHours;
    
    public CreateMachineJobDTO() {}
    
    public CreateMachineJobDTO(String assignedUserId, String machineId, String venue,
                               LocalDate startDate, LocalTime startTime, 
                               LocalDate endDate, LocalTime endTime, String description) {
        this.assignedUserId = assignedUserId;
        this.machineId = machineId;
        this.venue = venue;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.description = description;
    }
    
    public String getAssignedUserId() {
        return assignedUserId;
    }
    
    public void setAssignedUserId(String assignedUserId) {
        this.assignedUserId = assignedUserId;
    }
    
    public String getMachineId() {
        return machineId;
    }
    
    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
    
    public String getVenue() {
        return venue;
    }
    
    public void setVenue(String venue) {
        this.venue = venue;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Double getEstimatedDurationHours() {
        return estimatedDurationHours;
    }
    
    public void setEstimatedDurationHours(Double estimatedDurationHours) {
        this.estimatedDurationHours = estimatedDurationHours;
    }
}
