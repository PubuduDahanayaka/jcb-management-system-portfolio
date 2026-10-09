package com.example.demo.job_management.repository;

import com.example.demo.job_management.entity.JobStatus;
import com.example.demo.job_management.entity.MachineJob;
import com.example.demo.machine_management.entity.Machine;
import com.example.demo.user_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MachineJobRepository extends JpaRepository<MachineJob, Long> {
    
    List<MachineJob> findByAssignedUserAndIsActiveTrue(User assignedUser);
    
    List<MachineJob> findByMachineAndIsActiveTrue(Machine machine);
    
    List<MachineJob> findByStatusAndIsActiveTrue(JobStatus status);
    
    List<MachineJob> findByIsActiveTrue();
    
    @Query("SELECT j FROM MachineJob j WHERE j.assignedUser.userId = :userId AND j.isActive = true")
    List<MachineJob> findByUserIdAndIsActiveTrue(@Param("userId") String userId);
    
    @Query("SELECT j FROM MachineJob j WHERE j.machine.machineId = :machineId AND j.isActive = true")
    List<MachineJob> findByMachineIdAndIsActiveTrue(@Param("machineId") String machineId);
    
    @Query("SELECT j FROM MachineJob j WHERE j.venue = :venue AND j.isActive = true")
    List<MachineJob> findByVenueAndIsActiveTrue(@Param("venue") String venue);
    
    @Query("SELECT j FROM MachineJob j WHERE j.startDate BETWEEN :startDate AND :endDate AND j.isActive = true")
    List<MachineJob> findByStartDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT j FROM MachineJob j WHERE j.endDate BETWEEN :startDate AND :endDate AND j.isActive = true")
    List<MachineJob> findByEndDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT j FROM MachineJob j WHERE j.startDate <= :date AND j.endDate >= :date AND j.isActive = true")
    List<MachineJob> findJobsActiveOnDate(@Param("date") LocalDate date);
    
    @Query("SELECT j FROM MachineJob j WHERE j.machine.machineId = :machineId AND j.startDate <= :endDate AND j.endDate >= :startDate AND j.status IN ('SCHEDULED', 'IN_PROGRESS') AND j.isActive = true")
    List<MachineJob> findConflictingJobs(@Param("machineId") String machineId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT j FROM MachineJob j WHERE j.endDate < :date AND j.status IN ('SCHEDULED', 'IN_PROGRESS') AND j.isActive = true")
    List<MachineJob> findOverdueJobs(@Param("date") LocalDate date);
    
    @Query("SELECT j FROM MachineJob j WHERE j.status = 'IN_PROGRESS' AND j.isActive = true")
    List<MachineJob> findJobsInProgress();
    
    @Query("SELECT j FROM MachineJob j WHERE j.status = 'COMPLETED' AND j.isActive = true")
    List<MachineJob> findCompletedJobs();
    
    @Query("SELECT j FROM MachineJob j WHERE j.actualStartTime IS NOT NULL AND j.actualEndTime IS NULL AND j.isActive = true")
    List<MachineJob> findJobsCurrentlyRunning();
    
    @Query("SELECT COUNT(j) FROM MachineJob j WHERE j.assignedUser.userId = :userId AND j.status = :status AND j.isActive = true")
    Long countByUserIdAndStatus(@Param("userId") String userId, @Param("status") JobStatus status);
    
    @Query("SELECT COUNT(j) FROM MachineJob j WHERE j.machine.machineId = :machineId AND j.status = :status AND j.isActive = true")
    Long countByMachineIdAndStatus(@Param("machineId") String machineId, @Param("status") JobStatus status);
    
    @Query("SELECT j FROM MachineJob j WHERE j.createdAt >= :date AND j.isActive = true ORDER BY j.createdAt DESC")
    List<MachineJob> findRecentJobs(@Param("date") LocalDateTime date);
    
    @Query("SELECT j FROM MachineJob j WHERE j.estimatedDurationHours IS NOT NULL AND j.isActive = true ORDER BY j.estimatedDurationHours DESC")
    List<MachineJob> findJobsByEstimatedDuration();
    
    Optional<MachineJob> findByJobIdAndIsActiveTrue(Long jobId);
}
