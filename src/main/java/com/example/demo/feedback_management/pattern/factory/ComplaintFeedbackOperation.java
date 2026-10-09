package com.example.demo.feedback_management.pattern.factory;

import com.example.demo.feedback_management.entity.FeedbackType;

public class ComplaintFeedbackOperation implements FeedbackOperation {
    
    @Override
    public String execute(String operation) {
        return "Complaint feedback: " + operation + " - requires immediate attention";
    }
    
    @Override
    public FeedbackType getFeedbackType() {
        return FeedbackType.COMPLAINT;
    }
}
