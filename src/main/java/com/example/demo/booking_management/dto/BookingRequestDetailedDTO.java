package com.example.demo.booking_management.dto;

import com.example.demo.booking_management.entity.BookingRequestStatus;
import com.example.demo.machine_management.entity.FuelType;
import com.example.demo.machine_management.entity.MachineType;
import com.example.demo.user_management.entity.UserType;

import java.time.LocalDateTime;

public class BookingRequestDetailedDTO {
    
    private Long bookingId;
    private LocalDateTime requestedDate;
    private BookingRequestStatus status;
    private String additionalMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private String approvedBy;
    private LocalDateTime approvedAt;
    private String rejectionReason;
    
    private MachineInfo machineInfo;
    private UserInfo userInfo;
    
    public BookingRequestDetailedDTO() {}
    
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
    
    public Long getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    
    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }
    
    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }
    
    public BookingRequestStatus getStatus() {
        return status;
    }
    
    public void setStatus(BookingRequestStatus status) {
        this.status = status;
    }
    
    public String getAdditionalMessage() {
        return additionalMessage;
    }
    
    public void setAdditionalMessage(String additionalMessage) {
        this.additionalMessage = additionalMessage;
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
    
    public String getApprovedBy() {
        return approvedBy;
    }
    
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
    
    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }
    
    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }
    
    public String getRejectionReason() {
        return rejectionReason;
    }
    
    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
    
    public MachineInfo getMachineInfo() {
        return machineInfo;
    }
    
    public void setMachineInfo(MachineInfo machineInfo) {
        this.machineInfo = machineInfo;
    }
    
    public UserInfo getUserInfo() {
        return userInfo;
    }
    
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
