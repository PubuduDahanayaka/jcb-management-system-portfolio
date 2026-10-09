package com.example.demo.feedback_management.pattern.factory;

import com.example.demo.feedback_management.entity.FeedbackType;

public class GeneralFeedbackOperation implements FeedbackOperation {
    
    @Override
    public String execute(String operation) {
        return "General feedback: " + operation + " - general comments and observations";
    }
    
    @Override
    public FeedbackType getFeedbackType() {
        return FeedbackType.GENERAL_FEEDBACK;
    }
}
