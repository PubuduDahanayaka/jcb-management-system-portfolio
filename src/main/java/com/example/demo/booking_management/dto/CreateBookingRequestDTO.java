package com.example.demo.booking_management.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class CreateBookingRequestDTO {
    
    @NotNull(message = "Machine ID is required")
    private String machineId;
    
    @NotNull(message = "User ID is required")
    private String userId;
    
    @Future(message = "Requested date must be in the future")
    @NotNull(message = "Requested date is required")
    private LocalDateTime requestedDate;
    
    @Size(max = 500, message = "Additional message cannot exceed 500 characters")
    private String additionalMessage;
    
    public CreateBookingRequestDTO() {}
    
    public CreateBookingRequestDTO(String machineId, String userId, LocalDateTime requestedDate, String additionalMessage) {
        this.machineId = machineId;
        this.userId = userId;
        this.requestedDate = requestedDate;
        this.additionalMessage = additionalMessage;
    }
    
    public String getMachineId() {
        return machineId;
    }
    
    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }
    
    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }
    
    public String getAdditionalMessage() {
        return additionalMessage;
    }
    
    public void setAdditionalMessage(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }
}
