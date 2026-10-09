package com.example.demo.feedback_management.dto;

import com.example.demo.feedback_management.entity.FeedbackType;
import com.example.demo.user_management.entity.UserType;

import java.time.LocalDateTime;

public class FeedbackDetailedDTO {
    
    private Long feedbackId;
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
    
    private UserInfo userInfo;
    
    public FeedbackDetailedDTO() {}
    
    public static class UserInfo {
        private String userId;
        private String firstName;
        private String lastName;
        private String email;
        private String contactNumber;
        private UserType userType;
        private Boolean isActive;
        
        public UserInfo() {}
        
        public UserInfo(String userId, String firstName, String lastName, String email,
                       String contactNumber, UserType userType, Boolean isActive) {
            this.userId = userId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.contactNumber = contactNumber;
            this.userType = userType;
            this.isActive = isActive;
        }
        
        public String getUserId() {
            return userId;
        }
        
        public void setUserId(String userId) {
            this.userId = userId;
        }
        
        public String getFirstName() {
            return firstName;
        }
        
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        
        public String getLastName() {
            return lastName;
        }
        
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getContactNumber() {
            return contactNumber;
        }
        
        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }
        
        public UserType getUserType() {
            return userType;
        }
        
        public void setUserType(UserType userType) {
            this.userType = userType;
        }
        
        public Boolean getIsActive() {
            return isActive;
        }
        
        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }
    }
    
    public Long getFeedbackId() {
        return feedbackId;
    }
    
    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
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
    
    public UserInfo getUserInfo() {
        return userInfo;
    }
    
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
