package com.example.demo.feedback_management.dto;

import com.example.demo.feedback_management.entity.FeedbackType;

import java.time.LocalDateTime;

public class FeedbackResponseDTO {
    
    private Long feedbackId;
    private String userId;
    private String title;
    private String content;
    private FeedbackType feedbackType;
    private Integer rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private Boolean isAnonymous;
    private String adminResponse;
    private LocalDateTime adminResponseDate;
    private String respondedBy;
    
    public FeedbackResponseDTO() {}
    
    public FeedbackResponseDTO(Long feedbackId, String userId, String title, String content,
                              FeedbackType feedbackType, Integer rating, LocalDateTime createdAt,
                              LocalDateTime updatedAt, Boolean isActive, Boolean isAnonymous,
                              String adminResponse, LocalDateTime adminResponseDate, String respondedBy) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.feedbackType = feedbackType;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.isAnonymous = isAnonymous;
        this.adminResponse = adminResponse;
        this.adminResponseDate = adminResponseDate;
        this.respondedBy = respondedBy;
    }
    
    public Long getFeedbackId() {
        return feedbackId;
    }
    
    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
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
