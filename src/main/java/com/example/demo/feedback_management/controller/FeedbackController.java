package com.example.demo.feedback_management.controller;

import com.example.demo.feedback_management.dto.FeedbackDetailedDTO;
import com.example.demo.feedback_management.dto.FeedbackResponseDTO;
import com.example.demo.feedback_management.dto.CreateFeedbackDTO;
import com.example.demo.feedback_management.dto.UpdateFeedbackDTO;
import com.example.demo.feedback_management.entity.FeedbackType;
import com.example.demo.feedback_management.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@CrossOrigin(origins = "*")
public class FeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;
    
    @PostMapping
    public ResponseEntity<?> createFeedback(@Valid @RequestBody CreateFeedbackDTO createFeedbackDTO) {
        try {
            FeedbackResponseDTO feedback = feedbackService.createFeedback(createFeedbackDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(feedback);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/{feedbackId}")
    public ResponseEntity<?> getFeedbackById(@PathVariable Long feedbackId) {
        try {
            FeedbackResponseDTO feedback = feedbackService.getFeedbackById(feedbackId);
            return ResponseEntity.ok(feedback);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/{feedbackId}/detailed")
    public ResponseEntity<?> getFeedbackDetailedById(@PathVariable Long feedbackId) {
        try {
            FeedbackDetailedDTO feedback = feedbackService.getFeedbackDetailedById(feedbackId);
            return ResponseEntity.ok(feedback);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllFeedbacks() {
        try {
            List<FeedbackDetailedDTO> feedbacks = feedbackService.getAllFeedbacks();
            return ResponseEntity.ok(feedbacks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getFeedbacksByUser(@PathVariable String userId) {
        try {
            List<FeedbackResponseDTO> feedbacks = feedbackService.getFeedbacksByUser(userId);
            return ResponseEntity.ok(feedbacks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/type/{feedbackType}")
    public ResponseEntity<?> getFeedbacksByType(@PathVariable FeedbackType feedbackType) {
        try {
            List<FeedbackResponseDTO> feedbacks = feedbackService.getFeedbacksByType(feedbackType);
            return ResponseEntity.ok(feedbacks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/rating-range")
    public ResponseEntity<?> getFeedbacksByRatingRange(@RequestParam Integer minRating, 
                                                       @RequestParam Integer maxRating) {
        try {
            List<FeedbackResponseDTO> feedbacks = feedbackService.getFeedbacksByRatingRange(minRating, maxRating);
            return ResponseEntity.ok(feedbacks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/without-admin-response")
    public ResponseEntity<?> getFeedbacksWithoutAdminResponse() {
        try {
            List<FeedbackResponseDTO> feedbacks = feedbackService.getFeedbacksWithoutAdminResponse();
            return ResponseEntity.ok(feedbacks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{feedbackId}")
    public ResponseEntity<?> updateFeedback(@PathVariable Long feedbackId, 
                                            @Valid @RequestBody UpdateFeedbackDTO updateFeedbackDTO) {
        try {
            FeedbackResponseDTO feedback = feedbackService.updateFeedback(feedbackId, updateFeedbackDTO);
            return ResponseEntity.ok(feedback);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long feedbackId) {
        try {
            feedbackService.deleteFeedback(feedbackId);
            return ResponseEntity.ok("Feedback deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @PostMapping("/{feedbackId}/admin-response")
    public ResponseEntity<?> addAdminResponse(@PathVariable Long feedbackId, 
                                              @RequestParam String adminResponse,
                                              @RequestParam String respondedBy) {
        try {
            FeedbackResponseDTO feedback = feedbackService.addAdminResponse(feedbackId, adminResponse, respondedBy);
            return ResponseEntity.ok(feedback);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/average-rating")
    public ResponseEntity<?> getAverageRating() {
        try {
            Double averageRating = feedbackService.getAverageRating();
            return ResponseEntity.ok(averageRating);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/average-rating/{feedbackType}")
    public ResponseEntity<?> getAverageRatingByType(@PathVariable FeedbackType feedbackType) {
        try {
            Double averageRating = feedbackService.getAverageRatingByType(feedbackType);
            return ResponseEntity.ok(averageRating);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
}
