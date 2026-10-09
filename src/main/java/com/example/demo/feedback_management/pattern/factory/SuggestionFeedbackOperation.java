package com.example.demo.feedback_management.pattern.factory;

import com.example.demo.feedback_management.entity.FeedbackType;

public class SuggestionFeedbackOperation implements FeedbackOperation {
    
    @Override
    public String execute(String operation) {
        return "Suggestion feedback: " + operation + " - for improvement consideration";
    }
    
    @Override
    public FeedbackType getFeedbackType() {
        return FeedbackType.SUGGESTION;
    }
}
