package com.example.demo.feedback_management;

import com.example.demo.feedback_management.dto.CreateFeedbackDTO;
import com.example.demo.feedback_management.entity.FeedbackType;
import com.example.demo.feedback_management.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FeedbackManagementTestRunner implements CommandLineRunner {
    
    @Autowired
    private FeedbackService feedbackService;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Feedback Management System Test ===");
        
        try {
            CreateFeedbackDTO createFeedbackDTO = new CreateFeedbackDTO(
                    "CUS0001", "Great Service", "The machine booking system works perfectly!", 
                    FeedbackType.COMPLIMENT, 5
            );
            
            var createdFeedback = feedbackService.createFeedback(createFeedbackDTO);
            System.out.println("Created Feedback: " + createdFeedback.getFeedbackId() + " - Type: " + createdFeedback.getFeedbackType());
            
            var detailedFeedback = feedbackService.getFeedbackDetailedById(createdFeedback.getFeedbackId());
            System.out.println("Detailed Feedback: " + detailedFeedback.getFeedbackId() + 
                             " - User: " + detailedFeedback.getUserInfo().getFirstName() + " " + detailedFeedback.getUserInfo().getLastName() +
                             " - Rating: " + detailedFeedback.getRating());
            
            var feedbackWithResponse = feedbackService.addAdminResponse(createdFeedback.getFeedbackId(), 
                    "Thank you for your positive feedback!", "ADM0001");
            System.out.println("Added Admin Response: " + feedbackWithResponse.getFeedbackId() + " - Response: " + feedbackWithResponse.getAdminResponse());
            
            System.out.println("=== Feedback Management Test completed successfully ===");
        } catch (Exception e) {
            System.err.println("Feedback Management Test failed: " + e.getMessage());
        }
    }
}
