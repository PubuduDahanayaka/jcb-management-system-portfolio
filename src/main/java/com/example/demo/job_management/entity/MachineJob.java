package com.example.demo.job_management.entity;

import com.example.demo.machine_management.entity.Machine;
import com.example.demo.user_management.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "machine_jobs")
public class MachineJob {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id", nullable = false)
    @NotNull(message = "Assigned user is required")
    private User assignedUser;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    @NotNull(message = "Machine is required")
    private Machine machine;
    
    @NotBlank(message = "Venue is required")
    @Size(min = 3, max = 200, message = "Venue must be between 3 and 200 characters")
    @Column(name = "venue", nullable = false, length = 200)
    private String venue;
    
    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @NotNull(message = "Start time is required")
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;
    
    @NotNull(message = "End date is required")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @NotNull(message = "End time is required")
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatus status = JobStatus.SCHEDULED;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Column(name = "description", length = 500)
    private String description;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "actual_start_time")
    private LocalDateTime actualStartTime;
    
    @Column(name = "actual_end_time")
    private LocalDateTime actualEndTime;
    
    @Column(name = "completion_notes", length = 1000)
    private String completionNotes;
    
    @Column(name = "estimated_duration_hours")
    private Double estimatedDurationHours;
    
    @Column(name = "actual_duration_hours")
    private Double actualDurationHours;
    
    public MachineJob() {}
    
    public MachineJob(User assignedUser, Machine machine, String venue, 
                      LocalDate startDate, LocalTime startTime, 
                      LocalDate endDate, LocalTime endTime, String description) {
        this.assignedUser = assignedUser;
        this.machine = machine;
        this.venue = venue;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.description = description;
    }
    
    public Long getJobId() {
        return jobId;
    }
    
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
    
    public User getAssignedUser() {
        return assignedUser;
    }
    
    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }
    
    public Machine getMachine() {
        return machine;
    }
    
    public void setMachine(Machine machine) {
        this.machine = machine;
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
