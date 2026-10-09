package com.example.demo.feedback_management.dto;

import com.example.demo.feedback_management.entity.FeedbackType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateFeedbackDTO {
    
    @NotNull(message = "User ID is required")
    private String userId;
    
    @NotBlank(message = "Feedback title is required")
    @Size(min = 5, max = 100, message = "Feedback title must be between 5 and 100 characters")
    private String title;
    
    @NotBlank(message = "Feedback content is required")
    @Size(min = 10, max = 1000, message = "Feedback content must be between 10 and 1000 characters")
    private String content;
    
    @NotNull(message = "Feedback type is required")
    private FeedbackType feedbackType;
    
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;
    
    private Boolean isAnonymous = false;
    
    public CreateFeedbackDTO() {}
    
    public CreateFeedbackDTO(String userId, String title, String content, FeedbackType feedbackType, Integer rating) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.feedbackType = feedbackType;
        this.rating = rating;
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
    
    public Boolean getIsAnonymous() {
        return isAnonymous;
    }
    
    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }
}
