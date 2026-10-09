package com.example.demo.feedback_management.pattern.decorator;

import com.example.demo.feedback_management.pattern.factory.FeedbackOperation;

public class ValidationFeedbackOperationDecorator extends FeedbackOperationDecorator {
    
    public ValidationFeedbackOperationDecorator(FeedbackOperation feedbackOperation) {
        super(feedbackOperation);
    }
    
    @Override
    public String execute(String operation) {
        if (operation == null || operation.trim().isEmpty()) {
            throw new IllegalArgumentException("Feedback operation cannot be null or empty");
        }
        String result = super.execute(operation);
        return result + " [VALIDATED]";
    }
}
