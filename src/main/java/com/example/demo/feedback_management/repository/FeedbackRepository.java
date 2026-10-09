package com.example.demo.feedback_management.repository;

import com.example.demo.feedback_management.entity.Feedback;
import com.example.demo.feedback_management.entity.FeedbackType;
import com.example.demo.user_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    List<Feedback> findByUserAndIsActiveTrue(User user);
    
    List<Feedback> findByFeedbackTypeAndIsActiveTrue(FeedbackType feedbackType);
    
    List<Feedback> findByIsActiveTrue();
    
    @Query("SELECT f FROM Feedback f WHERE f.user.userId = :userId AND f.isActive = true")
    List<Feedback> findByUserIdAndIsActiveTrue(@Param("userId") String userId);
    
    @Query("SELECT f FROM Feedback f WHERE f.rating >= :minRating AND f.isActive = true")
    List<Feedback> findByRatingGreaterThanEqual(@Param("minRating") Integer minRating);
    
    @Query("SELECT f FROM Feedback f WHERE f.rating <= :maxRating AND f.isActive = true")
    List<Feedback> findByRatingLessThanEqual(@Param("maxRating") Integer maxRating);
    
    @Query("SELECT f FROM Feedback f WHERE f.rating BETWEEN :minRating AND :maxRating AND f.isActive = true")
    List<Feedback> findByRatingBetween(@Param("minRating") Integer minRating, @Param("maxRating") Integer maxRating);
    
    @Query("SELECT f FROM Feedback f WHERE f.isAnonymous = :isAnonymous AND f.isActive = true")
    List<Feedback> findByIsAnonymousAndIsActiveTrue(@Param("isAnonymous") Boolean isAnonymous);
    
    @Query("SELECT f FROM Feedback f WHERE f.adminResponse IS NOT NULL AND f.isActive = true")
    List<Feedback> findFeedbackWithAdminResponse();
    
    @Query("SELECT f FROM Feedback f WHERE f.adminResponse IS NULL AND f.isActive = true")
    List<Feedback> findFeedbackWithoutAdminResponse();
    
    @Query("SELECT f FROM Feedback f WHERE f.createdAt BETWEEN :startDate AND :endDate AND f.isActive = true")
    List<Feedback> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT f FROM Feedback f WHERE f.createdAt >= :date AND f.isActive = true ORDER BY f.createdAt DESC")
    List<Feedback> findRecentFeedbacks(@Param("date") LocalDateTime date);
    
    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.user.userId = :userId AND f.isActive = true")
    Long countByUserIdAndIsActiveTrue(@Param("userId") String userId);
    
    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.feedbackType = :feedbackType AND f.isActive = true")
    Long countByFeedbackTypeAndIsActiveTrue(@Param("feedbackType") FeedbackType feedbackType);
    
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.rating IS NOT NULL AND f.isActive = true")
    Double getAverageRating();
    
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.feedbackType = :feedbackType AND f.rating IS NOT NULL AND f.isActive = true")
    Double getAverageRatingByFeedbackType(@Param("feedbackType") FeedbackType feedbackType);
    
    Optional<Feedback> findByFeedbackIdAndIsActiveTrue(Long feedbackId);
}
