package com.example.demo.feedback_management.pattern.factory;

import com.example.demo.feedback_management.entity.FeedbackType;

public class ProductRatingFeedbackOperation implements FeedbackOperation {
    
    @Override
    public String execute(String operation) {
        return "Product rating feedback: " + operation + " - product quality evaluation";
    }
    
    @Override
    public FeedbackType getFeedbackType() {
        return FeedbackType.PRODUCT_RATING;
    }
}
