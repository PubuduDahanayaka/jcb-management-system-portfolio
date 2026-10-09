package com.example.demo.feedback_management.pattern.factory;

import com.example.demo.feedback_management.entity.FeedbackType;

public class FeatureRequestFeedbackOperation implements FeedbackOperation {
    
    @Override
    public String execute(String operation) {
        return "Feature request feedback: " + operation + " - new functionality requested";
    }
    
    @Override
    public FeedbackType getFeedbackType() {
        return FeedbackType.FEATURE_REQUEST;
    }
}
