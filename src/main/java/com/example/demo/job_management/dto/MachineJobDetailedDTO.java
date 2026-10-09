package com.example.demo.job_management.dto;

import com.example.demo.job_management.entity.JobStatus;
import com.example.demo.machine_management.entity.FuelType;
import com.example.demo.machine_management.entity.MachineType;
import com.example.demo.user_management.entity.UserType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MachineJobDetailedDTO {
    
    private Long jobId;
    private String venue;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private JobStatus status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private LocalDateTime actualStartTime;
    private LocalDateTime actualEndTime;
    private String completionNotes;
    private Double estimatedDurationHours;
    private Double actualDurationHours;
    
    private UserInfo assignedUserInfo;
    private MachineInfo machineInfo;
    
    public MachineJobDetailedDTO() {}
    
    public static class UserInfo {
        private String userId;
        private String firstName;
        private String lastName;
        private String email;
        private String contactNumber;
        private UserType userType;
        private Boolean isActive;
        
        public UserInfo() {}
        
        public UserInfo(String userId, String firstName, String lastName, String email,
                       String contactNumber, UserType userType, Boolean isActive) {
            this.userId = userId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.contactNumber = contactNumber;
            this.userType = userType;
            this.isActive = isActive;
        }
        
        public String getUserId() {
            return userId;
        }
        
        public void setUserId(String userId) {
            this.userId = userId;
        }
        
        public String getFirstName() {
            return firstName;
        }
        
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        
        public String getLastName() {
            return lastName;
        }
        
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getContactNumber() {
            return contactNumber;
        }
        
        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }
        
        public UserType getUserType() {
            return userType;
        }
        
        public void setUserType(UserType userType) {
            this.userType = userType;
        }
        
        public Boolean getIsActive() {
            return isActive;
        }
        
        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }
    }
    
    public static class MachineInfo {
        private String machineId;
        private MachineType machineType;
        private FuelType fuelType;
        private Double currentFuelLevel;
        private Double maxFuelCapacity;
        private Double operatingHours;
        private Boolean isActive;
        
        public MachineInfo() {}
        
        public MachineInfo(String machineId, MachineType machineType, FuelType fuelType,
                          Double currentFuelLevel, Double maxFuelCapacity, Double operatingHours,
                          Boolean isActive) {
            this.machineId = machineId;
            this.machineType = machineType;
            this.fuelType = fuelType;
            this.currentFuelLevel = currentFuelLevel;
            this.maxFuelCapacity = maxFuelCapacity;
            this.operatingHours = operatingHours;
            this.isActive = isActive;
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
        
        public Boolean getIsActive() {
            return isActive;
        }
        
        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }
    }
    
    public Long getJobId() {
        return jobId;
    }
    
    public void setJobId(Long jobId) {
        this.jobId = jobId;
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
    
    public JobStatus getStatus() {
        return status;
    }
    
    public void setStatus(JobStatus status) {
        this.status = status;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
    
    public LocalDateTime getActualStartTime() {
        return actualStartTime;
    }
    
    public void setActualStartTime(LocalDateTime actualStartTime) {
        this.actualStartTime = actualStartTime;
    }
    
    public LocalDateTime getActualEndTime() {
        return actualEndTime;
    }
    
    public void setActualEndTime(LocalDateTime actualEndTime) {
        this.actualEndTime = actualEndTime;
    }
    
    public String getCompletionNotes() {
        return completionNotes;
    }
    
    public void setCompletionNotes(String completionNotes) {
        this.completionNotes = completionNotes;
    }
    
    public Double getEstimatedDurationHours() {
        return estimatedDurationHours;
    }
    
    public void setEstimatedDurationHours(Double estimatedDurationHours) {
        this.estimatedDurationHours = estimatedDurationHours;
    }
    
    public Double getActualDurationHours() {
        return actualDurationHours;
    }
    
    public void setActualDurationHours(Double actualDurationHours) {
        this.actualDurationHours = actualDurationHours;
    }
    
    public UserInfo getAssignedUserInfo() {
        return assignedUserInfo;
    }
    
    public void setAssignedUserInfo(UserInfo assignedUserInfo) {
        this.assignedUserInfo = assignedUserInfo;
    }
    
    public MachineInfo getMachineInfo() {
        return machineInfo;
    }
    
    public void setMachineInfo(MachineInfo machineInfo) {
        this.machineInfo = machineInfo;
    }
}
