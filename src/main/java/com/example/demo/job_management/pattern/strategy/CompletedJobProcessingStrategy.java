package com.example.demo.job_management.pattern.strategy;

import com.example.demo.job_management.entity.MachineJob;
import org.springframework.stereotype.Component;

@Component
public class CompletedJobProcessingStrategy implements JobProcessingStrategy {
    
    @Override
    public String processJob(MachineJob job) {
        return "COMPLETED job processing: " + job.getJobId() + 
               " - Post-completion analysis and reporting - Venue: " + job.getVenue();
    }
    
    @Override
    public String getStrategyName() {
        return "COMPLETED_PROCESSING";
    }
}
