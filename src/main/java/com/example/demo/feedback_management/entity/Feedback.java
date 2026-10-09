package com.example.demo.feedback_management.entity;

import com.example.demo.user_management.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
public class Feedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required")
    private User user;
    
    @NotBlank(message = "Feedback title is required")
    @Size(min = 5, max = 100, message = "Feedback title must be between 5 and 100 characters")
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    
    @NotBlank(message = "Feedback content is required")
    @Size(min = 10, max = 1000, message = "Feedback content must be between 10 and 1000 characters")
    @Column(name = "content", nullable = false, length = 1000)
    private String content;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_type", nullable = false)
    private FeedbackType feedbackType;
    
    @Column(name = "rating")
    private Integer rating;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "is_anonymous", nullable = false)
    private Boolean isAnonymous = false;
    
    @Column(name = "admin_response", length = 500)
    private String adminResponse;
    
    @Column(name = "admin_response_date")
    private LocalDateTime adminResponseDate;
    
    @Column(name = "responded_by")
    private String respondedBy;
    
    public Feedback() {}
    
    public Feedback(User user, String title, String content, FeedbackType feedbackType, Integer rating) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.feedbackType = feedbackType;
        this.rating = rating;
    }
    
    public Long getFeedbackId() {
        return feedbackId;
    }
    
    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public FeedbackType getFeedbackType() {
        return feedbackType;
    }
    
    public void setFeedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
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
    
    public Boolean getIsAnonymous() {
        return isAnonymous;
    }
    
    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }
    
    public String getAdminResponse() {
        return adminResponse;
    }
    
    public void setAdminResponse(String adminResponse) {
        this.adminResponse = adminResponse;
    }
    
    public LocalDateTime getAdminResponseDate() {
        return adminResponseDate;
    }
    
    public void setAdminResponseDate(LocalDateTime adminResponseDate) {
        this.adminResponseDate = adminResponseDate;
    }
    
    public String getRespondedBy() {
        return respondedBy;
    }
    
    public void setRespondedBy(String respondedBy) {
        this.respondedBy = respondedBy;
    }
}
