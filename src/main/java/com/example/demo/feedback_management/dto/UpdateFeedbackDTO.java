package com.example.demo.feedback_management.dto;

import com.example.demo.feedback_management.entity.FeedbackType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class UpdateFeedbackDTO {
    
    @Size(min = 5, max = 100, message = "Feedback title must be between 5 and 100 characters")
    private String title;
    
    @Size(min = 10, max = 1000, message = "Feedback content must be between 10 and 1000 characters")
    private String content;
    
    private FeedbackType feedbackType;
    
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;
    
    private Boolean isAnonymous;
    
    @Size(max = 500, message = "Admin response cannot exceed 500 characters")
    private String adminResponse;
    
    private String respondedBy;
    
    private Boolean isActive;
    
    public UpdateFeedbackDTO() {}
    
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
    
    public String getRespondedBy() {
        return respondedBy;
    }
    
    public void setRespondedBy(String respondedBy) {
        this.respondedBy = respondedBy;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
