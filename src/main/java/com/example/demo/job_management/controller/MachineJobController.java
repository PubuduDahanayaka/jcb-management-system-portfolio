package com.example.demo.job_management.controller;

import com.example.demo.job_management.dto.CreateMachineJobDTO;
import com.example.demo.job_management.dto.MachineJobDetailedDTO;
import com.example.demo.job_management.dto.MachineJobResponseDTO;
import com.example.demo.job_management.dto.UpdateMachineJobDTO;
import com.example.demo.job_management.entity.JobStatus;
import com.example.demo.job_management.service.MachineJobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/machine-jobs")
@CrossOrigin(origins = "*")
public class MachineJobController {
    
    @Autowired
    private MachineJobService machineJobService;
    
    @PostMapping
    public ResponseEntity<?> createMachineJob(@Valid @RequestBody CreateMachineJobDTO createMachineJobDTO) {
        try {
            MachineJobResponseDTO machineJob = machineJobService.createMachineJob(createMachineJobDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(machineJob);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/{jobId}")
    public ResponseEntity<?> getMachineJobById(@PathVariable Long jobId) {
        try {
            MachineJobResponseDTO machineJob = machineJobService.getMachineJobById(jobId);
            return ResponseEntity.ok(machineJob);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/{jobId}/detailed")
    public ResponseEntity<?> getMachineJobDetailedById(@PathVariable Long jobId) {
        try {
            MachineJobDetailedDTO machineJob = machineJobService.getMachineJobDetailedById(jobId);
            return ResponseEntity.ok(machineJob);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllMachineJobs() {
        try {
            List<MachineJobResponseDTO> machineJobs = machineJobService.getAllMachineJobs();
            return ResponseEntity.ok(machineJobs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getJobsByUser(@PathVariable String userId) {
        try {
            List<MachineJobResponseDTO> machineJobs = machineJobService.getJobsByUser(userId);
            return ResponseEntity.ok(machineJobs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/machine/{machineId}")
    public ResponseEntity<?> getJobsByMachine(@PathVariable String machineId) {
        try {
            List<MachineJobResponseDTO> machineJobs = machineJobService.getJobsByMachine(machineId);
            return ResponseEntity.ok(machineJobs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getJobsByStatus(@PathVariable JobStatus status) {
        try {
            List<MachineJobResponseDTO> machineJobs = machineJobService.getJobsByStatus(status);
            return ResponseEntity.ok(machineJobs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/venue/{venue}")
    public ResponseEntity<?> getJobsByVenue(@PathVariable String venue) {
        try {
            List<MachineJobResponseDTO> machineJobs = machineJobService.getJobsByVenue(venue);
            return ResponseEntity.ok(machineJobs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<?> getJobsByDateRange(@RequestParam LocalDate startDate, 
                                                @RequestParam LocalDate endDate) {
        try {
            List<MachineJobResponseDTO> machineJobs = machineJobService.getJobsByDateRange(startDate, endDate);
            return ResponseEntity.ok(machineJobs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/overdue")
    public ResponseEntity<?> getOverdueJobs() {
        try {
            List<MachineJobResponseDTO> machineJobs = machineJobService.getOverdueJobs();
            return ResponseEntity.ok(machineJobs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/in-progress")
    public ResponseEntity<?> getJobsInProgress() {
        try {
            List<MachineJobResponseDTO> machineJobs = machineJobService.getJobsInProgress();
            return ResponseEntity.ok(machineJobs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{jobId}")
    public ResponseEntity<?> updateMachineJob(@PathVariable Long jobId, 
                                              @Valid @RequestBody UpdateMachineJobDTO updateMachineJobDTO) {
        try {
            MachineJobResponseDTO machineJob = machineJobService.updateMachineJob(jobId, updateMachineJobDTO);
            return ResponseEntity.ok(machineJob);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{jobId}")
    public ResponseEntity<?> deleteMachineJob(@PathVariable Long jobId) {
        try {
            machineJobService.deleteMachineJob(jobId);
            return ResponseEntity.ok("Machine job deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @PostMapping("/{jobId}/start")
    public ResponseEntity<?> startJob(@PathVariable Long jobId) {
        try {
            MachineJobResponseDTO machineJob = machineJobService.startJob(jobId);
            return ResponseEntity.ok(machineJob);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @PostMapping("/{jobId}/complete")
    public ResponseEntity<?> completeJob(@PathVariable Long jobId, 
                                          @RequestParam String completionNotes) {
        try {
            MachineJobResponseDTO machineJob = machineJobService.completeJob(jobId, completionNotes);
            return ResponseEntity.ok(machineJob);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
}
