package com.example.demo.feedback_management.pattern.factory;

import com.example.demo.feedback_management.entity.FeedbackType;

public class ServiceRatingFeedbackOperation implements FeedbackOperation {
    
    @Override
    public String execute(String operation) {
        return "Service rating feedback: " + operation + " - service quality evaluation";
    }
    
    @Override
    public FeedbackType getFeedbackType() {
        return FeedbackType.SERVICE_RATING;
    }
}
