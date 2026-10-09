package com.example.demo.feedback_management.pattern.factory;

import com.example.demo.feedback_management.entity.FeedbackType;
import org.springframework.stereotype.Component;

@Component
public class ConcreteFeedbackOperationFactory implements FeedbackOperationFactory {
    
    @Override
    public FeedbackOperation createOperation(FeedbackType feedbackType) {
        return switch (feedbackType) {
            case COMPLAINT -> new ComplaintFeedbackOperation();
            case SUGGESTION -> new SuggestionFeedbackOperation();
            case COMPLIMENT -> new ComplimentFeedbackOperation();
            case BUG_REPORT -> new BugReportFeedbackOperation();
            case FEATURE_REQUEST -> new FeatureRequestFeedbackOperation();
            case GENERAL_FEEDBACK -> new GeneralFeedbackOperation();
            case SERVICE_RATING -> new ServiceRatingFeedbackOperation();
            case PRODUCT_RATING -> new ProductRatingFeedbackOperation();
        };
    }
}
