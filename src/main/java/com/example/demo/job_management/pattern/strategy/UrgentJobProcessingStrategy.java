package com.example.demo.job_management.pattern.strategy;

import com.example.demo.job_management.entity.MachineJob;
import org.springframework.stereotype.Component;

@Component
public class UrgentJobProcessingStrategy implements JobProcessingStrategy {
    
    @Override
    public String processJob(MachineJob job) {
        return "URGENT processing for job: " + job.getJobId() + 
               " - Priority handling with immediate attention - Venue: " + job.getVenue();
    }
    
    @Override
    public String getStrategyName() {
        return "URGENT_PROCESSING";
    }
}
