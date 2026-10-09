package com.example.demo.feedback_management.pattern.decorator;

import com.example.demo.feedback_management.pattern.factory.FeedbackOperation;

public abstract class FeedbackOperationDecorator implements FeedbackOperation {
    
    protected FeedbackOperation feedbackOperation;
    
    public FeedbackOperationDecorator(FeedbackOperation feedbackOperation) {
        this.feedbackOperation = feedbackOperation;
    }
    
    @Override
    public String execute(String operation) {
        return feedbackOperation.execute(operation);
    }
    
    @Override
    public com.example.demo.feedback_management.entity.FeedbackType getFeedbackType() {
        return feedbackOperation.getFeedbackType();
    }
}
