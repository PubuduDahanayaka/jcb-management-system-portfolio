package com.example.demo.feedback_management.pattern.factory;

import com.example.demo.feedback_management.entity.FeedbackType;

public class ComplimentFeedbackOperation implements FeedbackOperation {
    
    @Override
    public String execute(String operation) {
        return "Compliment feedback: " + operation + " - positive recognition";
    }
    
    @Override
    public FeedbackType getFeedbackType() {
        return FeedbackType.COMPLIMENT;
    }
}
