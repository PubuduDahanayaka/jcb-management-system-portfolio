package com.example.demo.booking_management.dto;

import com.example.demo.booking_management.entity.BookingRequestStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UpdateBookingRequestDTO {
    
    @Future(message = "Requested date must be in the future")
    private LocalDateTime requestedDate;
    
    private BookingRequestStatus status;
    
    @Size(max = 500, message = "Additional message cannot exceed 500 characters")
    private String additionalMessage;
    
    private String approvedBy;
    
    private String rejectionReason;
    
    private Boolean isActive;
    
    public UpdateBookingRequestDTO() {}
    
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
    
    public String getApprovedBy() {
        return approvedBy;
    }
    
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
    
    public String getRejectionReason() {
        return rejectionReason;
    }
    
    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
