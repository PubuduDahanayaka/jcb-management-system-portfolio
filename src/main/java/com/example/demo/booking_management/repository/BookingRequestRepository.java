package com.example.demo.booking_management.repository;

import com.example.demo.booking_management.entity.BookingRequest;
import com.example.demo.booking_management.entity.BookingRequestStatus;
import com.example.demo.machine_management.entity.Machine;
import com.example.demo.user_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRequestRepository extends JpaRepository<BookingRequest, Long> {
    
    List<BookingRequest> findByUserAndIsActiveTrue(User user);
    
    List<BookingRequest> findByMachineAndIsActiveTrue(Machine machine);
    
    List<BookingRequest> findByStatusAndIsActiveTrue(BookingRequestStatus status);
    
    List<BookingRequest> findByIsActiveTrue();
    
    @Query("SELECT br FROM BookingRequest br WHERE br.user.userId = :userId AND br.isActive = true")
    List<BookingRequest> findByUserIdAndIsActiveTrue(@Param("userId") String userId);
    
    @Query("SELECT br FROM BookingRequest br WHERE br.machine.machineId = :machineId AND br.isActive = true")
    List<BookingRequest> findByMachineIdAndIsActiveTrue(@Param("machineId") String machineId);
    
    @Query("SELECT br FROM BookingRequest br WHERE br.requestedDate BETWEEN :startDate AND :endDate AND br.isActive = true")
    List<BookingRequest> findByRequestedDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT br FROM BookingRequest br WHERE br.requestedDate >= :date AND br.isActive = true ORDER BY br.requestedDate ASC")
    List<BookingRequest> findUpcomingBookings(@Param("date") LocalDateTime date);
    
    @Query("SELECT br FROM BookingRequest br WHERE br.machine.machineId = :machineId AND br.requestedDate = :requestedDate AND br.status IN ('PENDING', 'APPROVED') AND br.isActive = true")
    List<BookingRequest> findConflictingBookings(@Param("machineId") String machineId, @Param("requestedDate") LocalDateTime requestedDate);
    
    @Query("SELECT COUNT(br) FROM BookingRequest br WHERE br.user.userId = :userId AND br.status = :status AND br.isActive = true")
    Long countByUserIdAndStatus(@Param("userId") String userId, @Param("status") BookingRequestStatus status);
    
    @Query("SELECT br FROM BookingRequest br WHERE br.status = 'PENDING' AND br.isActive = true ORDER BY br.createdAt ASC")
    List<BookingRequest> findPendingRequestsOrderByCreatedAt();
    
    Optional<BookingRequest> findByBookingIdAndIsActiveTrue(Long bookingId);
}
