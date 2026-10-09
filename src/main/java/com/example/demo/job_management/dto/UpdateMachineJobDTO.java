package com.example.demo.job_management.dto;

import com.example.demo.job_management.entity.JobStatus;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UpdateMachineJobDTO {
    
    @Size(min = 3, max = 200, message = "Venue must be between 3 and 200 characters")
    private String venue;
    
    private LocalDate startDate;
    
    private LocalTime startTime;
    
    private LocalDate endDate;
    
    private LocalTime endTime;
    
    private JobStatus status;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    private LocalDateTime actualStartTime;
    
    private LocalDateTime actualEndTime;
    
    @Size(max = 1000, message = "Completion notes cannot exceed 1000 characters")
    private String completionNotes;
    
    private Double estimatedDurationHours;
    
    private Double actualDurationHours;
    
    private Boolean isActive;
    
    public UpdateMachineJobDTO() {}
    
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
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
