package com.example.demo.feedback_management.pattern.factory;

import com.example.demo.feedback_management.entity.FeedbackType;

public class BugReportFeedbackOperation implements FeedbackOperation {
    
    @Override
    public String execute(String operation) {
        return "Bug report feedback: " + operation + " - technical issue reported";
    }
    
    @Override
    public FeedbackType getFeedbackType() {
        return FeedbackType.BUG_REPORT;
    }
}
