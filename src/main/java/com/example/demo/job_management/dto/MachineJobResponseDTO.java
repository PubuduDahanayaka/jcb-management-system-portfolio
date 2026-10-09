package com.example.demo.job_management.dto;

import com.example.demo.job_management.entity.JobStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MachineJobResponseDTO {
    
    private Long jobId;
    private String assignedUserId;
    private String machineId;
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
    
    public MachineJobResponseDTO() {}
    
    public MachineJobResponseDTO(Long jobId, String assignedUserId, String machineId, 
                                 String venue, LocalDate startDate, LocalTime startTime,
                                 LocalDate endDate, LocalTime endTime, JobStatus status,
                                 String description, LocalDateTime createdAt, LocalDateTime updatedAt,
                                 Boolean isActive, LocalDateTime actualStartTime, LocalDateTime actualEndTime,
                                 String completionNotes, Double estimatedDurationHours, Double actualDurationHours) {
        this.jobId = jobId;
        this.assignedUserId = assignedUserId;
        this.machineId = machineId;
        this.venue = venue;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.actualStartTime = actualStartTime;
        this.actualEndTime = actualEndTime;
        this.completionNotes = completionNotes;
        this.estimatedDurationHours = estimatedDurationHours;
        this.actualDurationHours = actualDurationHours;
    }
    
    public Long getJobId() {
        return jobId;
    }
    
    public void setJobId(Long jobId) {
        this.jobId = jobId;
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
}
