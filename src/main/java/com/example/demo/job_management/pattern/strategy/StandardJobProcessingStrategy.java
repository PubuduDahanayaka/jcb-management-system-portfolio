package com.example.demo.job_management.pattern.strategy;

import com.example.demo.job_management.entity.MachineJob;
import org.springframework.stereotype.Component;

@Component
public class StandardJobProcessingStrategy implements JobProcessingStrategy {
    
    @Override
    public String processJob(MachineJob job) {
        return "Standard processing for job: " + job.getJobId() + 
               " - Venue: " + job.getVenue() + " - Status: " + job.getStatus();
    }
    
    @Override
    public String getStrategyName() {
        return "STANDARD_PROCESSING";
    }
}
