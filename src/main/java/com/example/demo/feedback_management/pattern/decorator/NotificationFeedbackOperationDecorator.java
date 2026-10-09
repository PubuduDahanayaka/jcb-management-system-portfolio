package com.example.demo.feedback_management.pattern.decorator;

import com.example.demo.feedback_management.pattern.factory.FeedbackOperation;

public class NotificationFeedbackOperationDecorator extends FeedbackOperationDecorator {
    
    public NotificationFeedbackOperationDecorator(FeedbackOperation feedbackOperation) {
        super(feedbackOperation);
    }
    
    @Override
    public String execute(String operation) {
        String result = super.execute(operation);
        System.out.println("Notification: " + getFeedbackType() + " feedback notification sent to admin");
        return result + " [NOTIFIED]";
    }
}
