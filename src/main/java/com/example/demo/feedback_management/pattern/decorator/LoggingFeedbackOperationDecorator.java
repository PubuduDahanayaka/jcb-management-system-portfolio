package com.example.demo.feedback_management.pattern.decorator;

import com.example.demo.feedback_management.pattern.factory.FeedbackOperation;

public class LoggingFeedbackOperationDecorator extends FeedbackOperationDecorator {
    
    public LoggingFeedbackOperationDecorator(FeedbackOperation feedbackOperation) {
        super(feedbackOperation);
    }
    
    @Override
    public String execute(String operation) {
        String result = super.execute(operation);
        System.out.println("Feedback Logging: " + getFeedbackType() + " executed operation: " + operation);
        return result + " [LOGGED]";
    }
}
