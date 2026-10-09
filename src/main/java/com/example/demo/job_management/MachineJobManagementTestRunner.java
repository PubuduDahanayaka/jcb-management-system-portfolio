package com.example.demo.job_management;

import com.example.demo.job_management.dto.CreateMachineJobDTO;
import com.example.demo.job_management.service.MachineJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class MachineJobManagementTestRunner implements CommandLineRunner {
    
    @Autowired
    private MachineJobService machineJobService;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Machine Job Management System Test ===");
        
        try {
            CreateMachineJobDTO createMachineJobDTO = new CreateMachineJobDTO(
                    "OPE0001", "EXC0001", "Construction Site A", 
                    LocalDate.now().plusDays(1), LocalTime.of(9, 0),
                    LocalDate.now().plusDays(1), LocalTime.of(17, 0),
                    "Excavation work for foundation"
            );
            
            var createdJob = machineJobService.createMachineJob(createMachineJobDTO);
            System.out.println("Created Job: " + createdJob.getJobId() + " - Venue: " + createdJob.getVenue() + " - Status: " + createdJob.getStatus());
            
            var detailedJob = machineJobService.getMachineJobDetailedById(createdJob.getJobId());
            System.out.println("Detailed Job: " + detailedJob.getJobId() + 
                             " - User: " + detailedJob.getAssignedUserInfo().getFirstName() + " " + detailedJob.getAssignedUserInfo().getLastName() +
                             " - Machine: " + detailedJob.getMachineInfo().getMachineType());
            
            var startedJob = machineJobService.startJob(createdJob.getJobId());
            System.out.println("Started Job: " + startedJob.getJobId() + " - Status: " + startedJob.getStatus());
            
            var completedJob = machineJobService.completeJob(createdJob.getJobId(), "Job completed successfully");
            System.out.println("Completed Job: " + completedJob.getJobId() + " - Status: " + completedJob.getStatus());
            
            System.out.println("=== Machine Job Management Test completed successfully ===");
        } catch (Exception e) {
            System.err.println("Machine Job Management Test failed: " + e.getMessage());
        }
    }
}
