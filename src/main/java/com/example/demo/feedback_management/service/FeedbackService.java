package com.example.demo.feedback_management.service;

import com.example.demo.feedback_management.dto.FeedbackDetailedDTO;
import com.example.demo.feedback_management.dto.FeedbackResponseDTO;
import com.example.demo.feedback_management.dto.CreateFeedbackDTO;
import com.example.demo.feedback_management.dto.UpdateFeedbackDTO;
import com.example.demo.feedback_management.entity.Feedback;
import com.example.demo.feedback_management.entity.FeedbackType;
import com.example.demo.feedback_management.pattern.decorator.LoggingFeedbackOperationDecorator;
import com.example.demo.feedback_management.pattern.decorator.NotificationFeedbackOperationDecorator;
import com.example.demo.feedback_management.pattern.decorator.ValidationFeedbackOperationDecorator;
import com.example.demo.feedback_management.pattern.factory.FeedbackOperation;
import com.example.demo.feedback_management.pattern.factory.FeedbackOperationFactory;
import com.example.demo.feedback_management.repository.FeedbackRepository;
import com.example.demo.user_management.entity.User;
import com.example.demo.user_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackService {
    
    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FeedbackOperationFactory feedbackOperationFactory;
    
    public FeedbackResponseDTO createFeedback(CreateFeedbackDTO createFeedbackDTO) {
        try {
            Optional<User> user = userRepository.findByUserIdAndIsActiveTrue(createFeedbackDTO.getUserId());
            if (user.isEmpty()) {
                throw new RuntimeException("User not found with ID: " + createFeedbackDTO.getUserId());
            }
            
            Feedback feedback = new Feedback();
            feedback.setUser(user.get());
            feedback.setTitle(createFeedbackDTO.getTitle());
            feedback.setContent(createFeedbackDTO.getContent());
            feedback.setFeedbackType(createFeedbackDTO.getFeedbackType());
            feedback.setRating(createFeedbackDTO.getRating());
            feedback.setIsAnonymous(createFeedbackDTO.getIsAnonymous());
            
            Feedback savedFeedback = feedbackRepository.save(feedback);
            
            FeedbackOperation operation = feedbackOperationFactory.createOperation(savedFeedback.getFeedbackType());
            operation = new ValidationFeedbackOperationDecorator(operation);
            operation = new NotificationFeedbackOperationDecorator(operation);
            operation = new LoggingFeedbackOperationDecorator(operation);
            operation.execute("CREATE_FEEDBACK");
            
            return convertToResponseDTO(savedFeedback);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create feedback: " + e.getMessage(), e);
        }
    }
    
    public FeedbackResponseDTO getFeedbackById(Long feedbackId) {
        try {
            Optional<Feedback> feedback = feedbackRepository.findByFeedbackIdAndIsActiveTrue(feedbackId);
            if (feedback.isEmpty()) {
                throw new RuntimeException("Feedback not found with ID: " + feedbackId);
            }
            
            FeedbackOperation operation = feedbackOperationFactory.createOperation(feedback.get().getFeedbackType());
            operation = new ValidationFeedbackOperationDecorator(operation);
            operation = new NotificationFeedbackOperationDecorator(operation);
            operation = new LoggingFeedbackOperationDecorator(operation);
            operation.execute("GET_FEEDBACK");
            
            return convertToResponseDTO(feedback.get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get feedback: " + e.getMessage(), e);
        }
    }
    
    public FeedbackDetailedDTO getFeedbackDetailedById(Long feedbackId) {
        try {
            Optional<Feedback> feedback = feedbackRepository.findByFeedbackIdAndIsActiveTrue(feedbackId);
            if (feedback.isEmpty()) {
                throw new RuntimeException("Feedback not found with ID: " + feedbackId);
            }
            
            FeedbackOperation operation = feedbackOperationFactory.createOperation(feedback.get().getFeedbackType());
            operation = new ValidationFeedbackOperationDecorator(operation);
            operation = new NotificationFeedbackOperationDecorator(operation);
            operation = new LoggingFeedbackOperationDecorator(operation);
            operation.execute("GET_FEEDBACK_DETAILED");
            
            return convertToDetailedDTO(feedback.get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get detailed feedback: " + e.getMessage(), e);
        }
    }
    
    public List<FeedbackDetailedDTO> getAllFeedbacks() {
        try {
            List<Feedback> feedbacks = feedbackRepository.findByIsActiveTrue();
            
            FeedbackOperation operation = feedbackOperationFactory.createOperation(FeedbackType.GENERAL_FEEDBACK);
            operation = new ValidationFeedbackOperationDecorator(operation);
            operation = new NotificationFeedbackOperationDecorator(operation);
            operation = new LoggingFeedbackOperationDecorator(operation);
            operation.execute("GET_ALL_FEEDBACKS");
            
            return feedbacks.stream()
                    .map(this::convertToDetailedDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all feedbacks: " + e.getMessage(), e);
        }
    }
    
    public List<FeedbackResponseDTO> getFeedbacksByUser(String userId) {
        try {
            List<Feedback> feedbacks = feedbackRepository.findByUserIdAndIsActiveTrue(userId);
            
            FeedbackOperation operation = feedbackOperationFactory.createOperation(FeedbackType.GENERAL_FEEDBACK);
            operation = new ValidationFeedbackOperationDecorator(operation);
            operation = new NotificationFeedbackOperationDecorator(operation);
            operation = new LoggingFeedbackOperationDecorator(operation);
            operation.execute("GET_FEEDBACKS_BY_USER");
            
            return feedbacks.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get feedbacks by user: " + e.getMessage(), e);
        }
    }
    
    public List<FeedbackResponseDTO> getFeedbacksByType(FeedbackType feedbackType) {
        try {
            List<Feedback> feedbacks = feedbackRepository.findByFeedbackTypeAndIsActiveTrue(feedbackType);
            
            FeedbackOperation operation = feedbackOperationFactory.createOperation(feedbackType);
            operation = new ValidationFeedbackOperationDecorator(operation);
            operation = new NotificationFeedbackOperationDecorator(operation);
            operation = new LoggingFeedbackOperationDecorator(operation);
            operation.execute("GET_FEEDBACKS_BY_TYPE");
            
            return feedbacks.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get feedbacks by type: " + e.getMessage(), e);
        }
    }
    
    public List<FeedbackResponseDTO> getFeedbacksByRatingRange(Integer minRating, Integer maxRating) {
        try {
            List<Feedback> feedbacks = feedbackRepository.findByRatingBetween(minRating, maxRating);
            
            FeedbackOperation operation = feedbackOperationFactory.createOperation(FeedbackType.SERVICE_RATING);
            operation = new ValidationFeedbackOperationDecorator(operation);
            operation = new NotificationFeedbackOperationDecorator(operation);
            operation = new LoggingFeedbackOperationDecorator(operation);
            operation.execute("GET_FEEDBACKS_BY_RATING_RANGE");
            
            return feedbacks.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get feedbacks by rating range: " + e.getMessage(), e);
        }
    }
    
    public List<FeedbackResponseDTO> getFeedbacksWithoutAdminResponse() {
        try {
            List<Feedback> feedbacks = feedbackRepository.findFeedbackWithoutAdminResponse();
            
            FeedbackOperation operation = feedbackOperationFactory.createOperation(FeedbackType.COMPLAINT);
            operation = new ValidationFeedbackOperationDecorator(operation);
            operation = new NotificationFeedbackOperationDecorator(operation);
            operation = new LoggingFeedbackOperationDecorator(operation);
            operation.execute("GET_FEEDBACKS_WITHOUT_ADMIN_RESPONSE");
            
            return feedbacks.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get feedbacks without admin response: " + e.getMessage(), e);
        }
    }
    
    public FeedbackResponseDTO updateFeedback(Long feedbackId, UpdateFeedbackDTO updateFeedbackDTO) {
        try {
            Optional<Feedback> feedbackOptional = feedbackRepository.findByFeedbackIdAndIsActiveTrue(feedbackId);
            if (feedbackOptional.isEmpty()) {
                throw new RuntimeException("Feedback not found with ID: " + feedbackId);
            }
            
            Feedback feedback = feedbackOptional.get();
            
            if (updateFeedbackDTO.getTitle() != null) {
                feedback.setTitle(updateFeedbackDTO.getTitle());
            }
            if (updateFeedbackDTO.getContent() != null) {
                feedback.setContent(updateFeedbackDTO.getContent());
            }
            if (updateFeedbackDTO.getFeedbackType() != null) {
                feedback.setFeedbackType(updateFeedbackDTO.getFeedbackType());
            }
            if (updateFeedbackDTO.getRating() != null) {
                feedback.setRating(updateFeedbackDTO.getRating());
            }
            if (updateFeedbackDTO.getIsAnonymous() != null) {
                feedback.setIsAnonymous(updateFeedbackDTO.getIsAnonymous());
            }
            if (updateFeedbackDTO.getAdminResponse() != null) {
                feedback.setAdminResponse(updateFeedbackDTO.getAdminResponse());
                feedback.setAdminResponseDate(LocalDateTime.now());
                feedback.setRespondedBy(updateFeedbackDTO.getRespondedBy());
            }
            if (updateFeedbackDTO.getIsActive() != null) {
                feedback.setIsActive(updateFeedbackDTO.getIsActive());
            }
            
            Feedback updatedFeedback = feedbackRepository.save(feedback);
            
            FeedbackOperation operation = feedbackOperationFactory.createOperation(updatedFeedback.getFeedbackType());
            operation = new ValidationFeedbackOperationDecorator(operation);
            operation = new NotificationFeedbackOperationDecorator(operation);
            operation = new LoggingFeedbackOperationDecorator(operation);
            operation.execute("UPDATE_FEEDBACK");
            
            return convertToResponseDTO(updatedFeedback);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update feedback: " + e.getMessage(), e);
        }
    }
    
    public void deleteFeedback(Long feedbackId) {
        try {
            Optional<Feedback> feedbackOptional = feedbackRepository.findByFeedbackIdAndIsActiveTrue(feedbackId);
            if (feedbackOptional.isEmpty()) {
                throw new RuntimeException("Feedback not found with ID: " + feedbackId);
            }
            
            Feedback feedback = feedbackOptional.get();
            feedback.setIsActive(false);
            feedbackRepository.save(feedback);
            
            FeedbackOperation operation = feedbackOperationFactory.createOperation(feedback.getFeedbackType());
            operation = new ValidationFeedbackOperationDecorator(operation);
            operation = new NotificationFeedbackOperationDecorator(operation);
            operation = new LoggingFeedbackOperationDecorator(operation);
            operation.execute("DELETE_FEEDBACK");
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete feedback: " + e.getMessage(), e);
        }
    }
    
    public FeedbackResponseDTO addAdminResponse(Long feedbackId, String adminResponse, String respondedBy) {
        try {
            Optional<Feedback> feedbackOptional = feedbackRepository.findByFeedbackIdAndIsActiveTrue(feedbackId);
            if (feedbackOptional.isEmpty()) {
                throw new RuntimeException("Feedback not found with ID: " + feedbackId);
            }
            
            Feedback feedback = feedbackOptional.get();
            feedback.setAdminResponse(adminResponse);
            feedback.setAdminResponseDate(LocalDateTime.now());
            feedback.setRespondedBy(respondedBy);
            
            Feedback updatedFeedback = feedbackRepository.save(feedback);
            
            FeedbackOperation operation = feedbackOperationFactory.createOperation(updatedFeedback.getFeedbackType());
            operation = new ValidationFeedbackOperationDecorator(operation);
            operation = new NotificationFeedbackOperationDecorator(operation);
            operation = new LoggingFeedbackOperationDecorator(operation);
            operation.execute("ADD_ADMIN_RESPONSE");
            
            return convertToResponseDTO(updatedFeedback);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add admin response: " + e.getMessage(), e);
        }
    }
    
    public Double getAverageRating() {
        try {
            Double averageRating = feedbackRepository.getAverageRating();
            return averageRating != null ? averageRating : 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get average rating: " + e.getMessage(), e);
        }
    }
    
    public Double getAverageRatingByType(FeedbackType feedbackType) {
        try {
            Double averageRating = feedbackRepository.getAverageRatingByFeedbackType(feedbackType);
            return averageRating != null ? averageRating : 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get average rating by type: " + e.getMessage(), e);
        }
    }
    
    private FeedbackResponseDTO convertToResponseDTO(Feedback feedback) {
        return new FeedbackResponseDTO(
                feedback.getFeedbackId(),
                feedback.getUser().getUserId(),
                feedback.getTitle(),
                feedback.getContent(),
                feedback.getFeedbackType(),
                feedback.getRating(),
                feedback.getCreatedAt(),
                feedback.getUpdatedAt(),
                feedback.getIsActive(),
                feedback.getIsAnonymous(),
                feedback.getAdminResponse(),
                feedback.getAdminResponseDate(),
                feedback.getRespondedBy()
        );
    }
    
    private FeedbackDetailedDTO convertToDetailedDTO(Feedback feedback) {
        FeedbackDetailedDTO detailedDTO = new FeedbackDetailedDTO();
        detailedDTO.setFeedbackId(feedback.getFeedbackId());
        detailedDTO.setTitle(feedback.getTitle());
        detailedDTO.setContent(feedback.getContent());
        detailedDTO.setFeedbackType(feedback.getFeedbackType());
        detailedDTO.setRating(feedback.getRating());
        detailedDTO.setCreatedAt(feedback.getCreatedAt());
        detailedDTO.setUpdatedAt(feedback.getUpdatedAt());
        detailedDTO.setIsActive(feedback.getIsActive());
        detailedDTO.setIsAnonymous(feedback.getIsAnonymous());
        detailedDTO.setAdminResponse(feedback.getAdminResponse());
        detailedDTO.setAdminResponseDate(feedback.getAdminResponseDate());
        detailedDTO.setRespondedBy(feedback.getRespondedBy());
        
        User user = feedback.getUser();
        FeedbackDetailedDTO.UserInfo userInfo = new FeedbackDetailedDTO.UserInfo(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getContactNumber(),
                user.getUserType(),
                user.getIsActive()
        );
        detailedDTO.setUserInfo(userInfo);
        
        return detailedDTO;
    }
}
