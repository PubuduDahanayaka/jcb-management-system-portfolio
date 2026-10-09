package com.example.demo.feedback_management.pattern.factory;

import com.example.demo.feedback_management.entity.FeedbackType;

public interface FeedbackOperation {
    String execute(String operation);
    FeedbackType getFeedbackType();
}
