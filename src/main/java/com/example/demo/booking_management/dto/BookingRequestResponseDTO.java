package com.example.demo.booking_management.dto;

import com.example.demo.booking_management.entity.BookingRequestStatus;

import java.time.LocalDateTime;

public class BookingRequestResponseDTO {
    
    private Long bookingId;
    private String machineId;
    private String userId;
    private LocalDateTime requestedDate;
    private BookingRequestStatus status;
    private String additionalMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private String approvedBy;
    private LocalDateTime approvedAt;
    private String rejectionReason;
    
    public BookingRequestResponseDTO() {}
    
    public BookingRequestResponseDTO(Long bookingId, String machineId, String userId, 
                                   LocalDateTime requestedDate, BookingRequestStatus status,
                                   String additionalMessage, LocalDateTime createdAt, 
                                   LocalDateTime updatedAt, Boolean isActive, String approvedBy,
                                   LocalDateTime approvedAt, String rejectionReason) {
        this.bookingId = bookingId;
        this.machineId = machineId;
        this.userId = userId;
        this.requestedDate = requestedDate;
        this.status = status;
        this.additionalMessage = additionalMessage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
        this.rejectionReason = rejectionReason;
    }
    
    public Long getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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
}
