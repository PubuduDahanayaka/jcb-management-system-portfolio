package com.example.demo.job_management.service;

import com.example.demo.job_management.dto.CreateMachineJobDTO;
import com.example.demo.job_management.dto.MachineJobDetailedDTO;
import com.example.demo.job_management.dto.MachineJobResponseDTO;
import com.example.demo.job_management.dto.UpdateMachineJobDTO;
import com.example.demo.job_management.entity.JobStatus;
import com.example.demo.job_management.entity.MachineJob;
import com.example.demo.job_management.pattern.factory.JobOperation;
import com.example.demo.job_management.pattern.factory.JobOperationFactory;
import com.example.demo.job_management.pattern.strategy.JobProcessingContext;
import com.example.demo.job_management.repository.MachineJobRepository;
import com.example.demo.machine_management.entity.Machine;
import com.example.demo.machine_management.repository.MachineRepository;
import com.example.demo.user_management.entity.User;
import com.example.demo.user_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MachineJobService {
    
    @Autowired
    private MachineJobRepository machineJobRepository;
    
    @Autowired
    private MachineRepository machineRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JobOperationFactory jobOperationFactory;
    
    @Autowired
    private JobProcessingContext jobProcessingContext;
    
    public MachineJobResponseDTO createMachineJob(CreateMachineJobDTO createMachineJobDTO) {
        try {
            Optional<User> user = userRepository.findByUserIdAndIsActiveTrue(createMachineJobDTO.getAssignedUserId());
            if (user.isEmpty()) {
                throw new RuntimeException("User not found with ID: " + createMachineJobDTO.getAssignedUserId());
            }
            
            Optional<Machine> machine = machineRepository.findByMachineIdAndIsActiveTrue(createMachineJobDTO.getMachineId());
            if (machine.isEmpty()) {
                throw new RuntimeException("Machine not found with ID: " + createMachineJobDTO.getMachineId());
            }
            
            List<MachineJob> conflictingJobs = machineJobRepository.findConflictingJobs(
                    createMachineJobDTO.getMachineId(), 
                    createMachineJobDTO.getStartDate(), 
                    createMachineJobDTO.getEndDate());
            if (!conflictingJobs.isEmpty()) {
                throw new RuntimeException("Machine is already assigned to another job during the specified time period");
            }
            
            MachineJob machineJob = new MachineJob();
            machineJob.setAssignedUser(user.get());
            machineJob.setMachine(machine.get());
            machineJob.setVenue(createMachineJobDTO.getVenue());
            machineJob.setStartDate(createMachineJobDTO.getStartDate());
            machineJob.setStartTime(createMachineJobDTO.getStartTime());
            machineJob.setEndDate(createMachineJobDTO.getEndDate());
            machineJob.setEndTime(createMachineJobDTO.getEndTime());
            machineJob.setDescription(createMachineJobDTO.getDescription());
            machineJob.setEstimatedDurationHours(createMachineJobDTO.getEstimatedDurationHours());
            
            MachineJob savedMachineJob = machineJobRepository.save(machineJob);
            
            JobOperation operation = jobOperationFactory.createOperation(savedMachineJob.getStatus());
            operation.execute("CREATE_MACHINE_JOB");
            
            String processingResult = jobProcessingContext.processJobWithStrategy(savedMachineJob);
            System.out.println("Job Processing: " + processingResult);
            
            return convertToResponseDTO(savedMachineJob);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create machine job: " + e.getMessage(), e);
        }
    }
    
    public MachineJobResponseDTO getMachineJobById(Long jobId) {
        try {
            Optional<MachineJob> machineJob = machineJobRepository.findByJobIdAndIsActiveTrue(jobId);
            if (machineJob.isEmpty()) {
                throw new RuntimeException("Machine job not found with ID: " + jobId);
            }
            
            JobOperation operation = jobOperationFactory.createOperation(machineJob.get().getStatus());
            operation.execute("GET_MACHINE_JOB");
            
            String processingResult = jobProcessingContext.processJobWithStrategy(machineJob.get());
            System.out.println("Job Processing: " + processingResult);
            
            return convertToResponseDTO(machineJob.get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get machine job: " + e.getMessage(), e);
        }
    }
    
    public MachineJobDetailedDTO getMachineJobDetailedById(Long jobId) {
        try {
            Optional<MachineJob> machineJob = machineJobRepository.findByJobIdAndIsActiveTrue(jobId);
            if (machineJob.isEmpty()) {
                throw new RuntimeException("Machine job not found with ID: " + jobId);
            }
            
            JobOperation operation = jobOperationFactory.createOperation(machineJob.get().getStatus());
            operation.execute("GET_MACHINE_JOB_DETAILED");
            
            String processingResult = jobProcessingContext.processJobWithStrategy(machineJob.get());
            System.out.println("Job Processing: " + processingResult);
            
            return convertToDetailedDTO(machineJob.get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get detailed machine job: " + e.getMessage(), e);
        }
    }
    
    public List<MachineJobResponseDTO> getAllMachineJobs() {
        try {
            List<MachineJob> machineJobs = machineJobRepository.findByIsActiveTrue();
            
            JobOperation operation = jobOperationFactory.createOperation(JobStatus.SCHEDULED);
            operation.execute("GET_ALL_MACHINE_JOBS");
            
            return machineJobs.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all machine jobs: " + e.getMessage(), e);
        }
    }
    
    public List<MachineJobResponseDTO> getJobsByUser(String userId) {
        try {
            List<MachineJob> machineJobs = machineJobRepository.findByUserIdAndIsActiveTrue(userId);
            
            JobOperation operation = jobOperationFactory.createOperation(JobStatus.SCHEDULED);
            operation.execute("GET_JOBS_BY_USER");
            
            return machineJobs.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get jobs by user: " + e.getMessage(), e);
        }
    }
    
    public List<MachineJobResponseDTO> getJobsByMachine(String machineId) {
        try {
            List<MachineJob> machineJobs = machineJobRepository.findByMachineIdAndIsActiveTrue(machineId);
            
            JobOperation operation = jobOperationFactory.createOperation(JobStatus.SCHEDULED);
            operation.execute("GET_JOBS_BY_MACHINE");
            
            return machineJobs.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get jobs by machine: " + e.getMessage(), e);
        }
    }
    
    public List<MachineJobResponseDTO> getJobsByStatus(JobStatus status) {
        try {
            List<MachineJob> machineJobs = machineJobRepository.findByStatusAndIsActiveTrue(status);
            
            JobOperation operation = jobOperationFactory.createOperation(status);
            operation.execute("GET_JOBS_BY_STATUS");
            
            return machineJobs.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get jobs by status: " + e.getMessage(), e);
        }
    }
    
    public List<MachineJobResponseDTO> getJobsByVenue(String venue) {
        try {
            List<MachineJob> machineJobs = machineJobRepository.findByVenueAndIsActiveTrue(venue);
            
            JobOperation operation = jobOperationFactory.createOperation(JobStatus.SCHEDULED);
            operation.execute("GET_JOBS_BY_VENUE");
            
            return machineJobs.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get jobs by venue: " + e.getMessage(), e);
        }
    }
    
    public List<MachineJobResponseDTO> getJobsByDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            List<MachineJob> machineJobs = machineJobRepository.findByStartDateBetween(startDate, endDate);
            
            JobOperation operation = jobOperationFactory.createOperation(JobStatus.SCHEDULED);
            operation.execute("GET_JOBS_BY_DATE_RANGE");
            
            return machineJobs.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get jobs by date range: " + e.getMessage(), e);
        }
    }
    
    public List<MachineJobResponseDTO> getOverdueJobs() {
        try {
            List<MachineJob> machineJobs = machineJobRepository.findOverdueJobs(LocalDate.now());
            
            JobOperation operation = jobOperationFactory.createOperation(JobStatus.OVERDUE);
            operation.execute("GET_OVERDUE_JOBS");
            
            return machineJobs.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get overdue jobs: " + e.getMessage(), e);
        }
    }
    
    public List<MachineJobResponseDTO> getJobsInProgress() {
        try {
            List<MachineJob> machineJobs = machineJobRepository.findJobsInProgress();
            
            JobOperation operation = jobOperationFactory.createOperation(JobStatus.IN_PROGRESS);
            operation.execute("GET_JOBS_IN_PROGRESS");
            
            return machineJobs.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get jobs in progress: " + e.getMessage(), e);
        }
    }
    
    public MachineJobResponseDTO updateMachineJob(Long jobId, UpdateMachineJobDTO updateMachineJobDTO) {
        try {
            Optional<MachineJob> machineJobOptional = machineJobRepository.findByJobIdAndIsActiveTrue(jobId);
            if (machineJobOptional.isEmpty()) {
                throw new RuntimeException("Machine job not found with ID: " + jobId);
            }
            
            MachineJob machineJob = machineJobOptional.get();
            
            if (updateMachineJobDTO.getVenue() != null) {
                machineJob.setVenue(updateMachineJobDTO.getVenue());
            }
            if (updateMachineJobDTO.getStartDate() != null) {
                machineJob.setStartDate(updateMachineJobDTO.getStartDate());
            }
            if (updateMachineJobDTO.getStartTime() != null) {
                machineJob.setStartTime(updateMachineJobDTO.getStartTime());
            }
            if (updateMachineJobDTO.getEndDate() != null) {
                machineJob.setEndDate(updateMachineJobDTO.getEndDate());
            }
            if (updateMachineJobDTO.getEndTime() != null) {
                machineJob.setEndTime(updateMachineJobDTO.getEndTime());
            }
            if (updateMachineJobDTO.getStatus() != null) {
                machineJob.setStatus(updateMachineJobDTO.getStatus());
            }
            if (updateMachineJobDTO.getDescription() != null) {
                machineJob.setDescription(updateMachineJobDTO.getDescription());
            }
            if (updateMachineJobDTO.getActualStartTime() != null) {
                machineJob.setActualStartTime(updateMachineJobDTO.getActualStartTime());
            }
            if (updateMachineJobDTO.getActualEndTime() != null) {
                machineJob.setActualEndTime(updateMachineJobDTO.getActualEndTime());
            }
            if (updateMachineJobDTO.getCompletionNotes() != null) {
                machineJob.setCompletionNotes(updateMachineJobDTO.getCompletionNotes());
            }
            if (updateMachineJobDTO.getEstimatedDurationHours() != null) {
                machineJob.setEstimatedDurationHours(updateMachineJobDTO.getEstimatedDurationHours());
            }
            if (updateMachineJobDTO.getActualDurationHours() != null) {
                machineJob.setActualDurationHours(updateMachineJobDTO.getActualDurationHours());
            }
            if (updateMachineJobDTO.getIsActive() != null) {
                machineJob.setIsActive(updateMachineJobDTO.getIsActive());
            }
            
            MachineJob updatedMachineJob = machineJobRepository.save(machineJob);
            
            JobOperation operation = jobOperationFactory.createOperation(updatedMachineJob.getStatus());
            operation.execute("UPDATE_MACHINE_JOB");
            
            String processingResult = jobProcessingContext.processJobWithStrategy(updatedMachineJob);
            System.out.println("Job Processing: " + processingResult);
            
            return convertToResponseDTO(updatedMachineJob);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update machine job: " + e.getMessage(), e);
        }
    }
    
    public void deleteMachineJob(Long jobId) {
        try {
            Optional<MachineJob> machineJobOptional = machineJobRepository.findByJobIdAndIsActiveTrue(jobId);
            if (machineJobOptional.isEmpty()) {
                throw new RuntimeException("Machine job not found with ID: " + jobId);
            }
            
            MachineJob machineJob = machineJobOptional.get();
            machineJob.setIsActive(false);
            machineJobRepository.save(machineJob);
            
            JobOperation operation = jobOperationFactory.createOperation(machineJob.getStatus());
            operation.execute("DELETE_MACHINE_JOB");
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete machine job: " + e.getMessage(), e);
        }
    }
    
    public MachineJobResponseDTO startJob(Long jobId) {
        try {
            Optional<MachineJob> machineJobOptional = machineJobRepository.findByJobIdAndIsActiveTrue(jobId);
            if (machineJobOptional.isEmpty()) {
                throw new RuntimeException("Machine job not found with ID: " + jobId);
            }
            
            MachineJob machineJob = machineJobOptional.get();
            machineJob.setStatus(JobStatus.IN_PROGRESS);
            machineJob.setActualStartTime(LocalDateTime.now());
            
            MachineJob updatedMachineJob = machineJobRepository.save(machineJob);
            
            JobOperation operation = jobOperationFactory.createOperation(updatedMachineJob.getStatus());
            operation.execute("START_JOB");
            
            String processingResult = jobProcessingContext.processJobWithStrategy(updatedMachineJob);
            System.out.println("Job Processing: " + processingResult);
            
            return convertToResponseDTO(updatedMachineJob);
        } catch (Exception e) {
            throw new RuntimeException("Failed to start job: " + e.getMessage(), e);
        }
    }
    
    public MachineJobResponseDTO completeJob(Long jobId, String completionNotes) {
        try {
            Optional<MachineJob> machineJobOptional = machineJobRepository.findByJobIdAndIsActiveTrue(jobId);
            if (machineJobOptional.isEmpty()) {
                throw new RuntimeException("Machine job not found with ID: " + jobId);
            }
            
            MachineJob machineJob = machineJobOptional.get();
            machineJob.setStatus(JobStatus.COMPLETED);
            machineJob.setActualEndTime(LocalDateTime.now());
            machineJob.setCompletionNotes(completionNotes);
            
            MachineJob updatedMachineJob = machineJobRepository.save(machineJob);
            
            JobOperation operation = jobOperationFactory.createOperation(updatedMachineJob.getStatus());
            operation.execute("COMPLETE_JOB");
            
            String processingResult = jobProcessingContext.processJobWithStrategy(updatedMachineJob);
            System.out.println("Job Processing: " + processingResult);
            
            return convertToResponseDTO(updatedMachineJob);
        } catch (Exception e) {
            throw new RuntimeException("Failed to complete job: " + e.getMessage(), e);
        }
    }
    
    private MachineJobResponseDTO convertToResponseDTO(MachineJob machineJob) {
        return new MachineJobResponseDTO(
                machineJob.getJobId(),
                machineJob.getAssignedUser().getUserId(),
                machineJob.getMachine().getMachineId(),
                machineJob.getVenue(),
                machineJob.getStartDate(),
                machineJob.getStartTime(),
                machineJob.getEndDate(),
                machineJob.getEndTime(),
                machineJob.getStatus(),
                machineJob.getDescription(),
                machineJob.getCreatedAt(),
                machineJob.getUpdatedAt(),
                machineJob.getIsActive(),
                machineJob.getActualStartTime(),
                machineJob.getActualEndTime(),
                machineJob.getCompletionNotes(),
                machineJob.getEstimatedDurationHours(),
                machineJob.getActualDurationHours()
        );
    }
    
    private MachineJobDetailedDTO convertToDetailedDTO(MachineJob machineJob) {
        MachineJobDetailedDTO detailedDTO = new MachineJobDetailedDTO();
        detailedDTO.setJobId(machineJob.getJobId());
        detailedDTO.setVenue(machineJob.getVenue());
        detailedDTO.setStartDate(machineJob.getStartDate());
        detailedDTO.setStartTime(machineJob.getStartTime());
        detailedDTO.setEndDate(machineJob.getEndDate());
        detailedDTO.setEndTime(machineJob.getEndTime());
        detailedDTO.setStatus(machineJob.getStatus());
        detailedDTO.setDescription(machineJob.getDescription());
        detailedDTO.setCreatedAt(machineJob.getCreatedAt());
        detailedDTO.setUpdatedAt(machineJob.getUpdatedAt());
        detailedDTO.setIsActive(machineJob.getIsActive());
        detailedDTO.setActualStartTime(machineJob.getActualStartTime());
        detailedDTO.setActualEndTime(machineJob.getActualEndTime());
        detailedDTO.setCompletionNotes(machineJob.getCompletionNotes());
        detailedDTO.setEstimatedDurationHours(machineJob.getEstimatedDurationHours());
        detailedDTO.setActualDurationHours(machineJob.getActualDurationHours());
        
        User user = machineJob.getAssignedUser();
        MachineJobDetailedDTO.UserInfo userInfo = new MachineJobDetailedDTO.UserInfo(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getContactNumber(),
                user.getUserType(),
                user.getIsActive()
        );
        detailedDTO.setAssignedUserInfo(userInfo);
        
        Machine machine = machineJob.getMachine();
        MachineJobDetailedDTO.MachineInfo machineInfo = new MachineJobDetailedDTO.MachineInfo(
                machine.getMachineId(),
                machine.getMachineType(),
                machine.getFuelType(),
                machine.getCurrentFuelLevel(),
                machine.getMaxFuelCapacity(),
                machine.getOperatingHours(),
                machine.getIsActive()
        );
        detailedDTO.setMachineInfo(machineInfo);
        
        return detailedDTO;
    }
}
