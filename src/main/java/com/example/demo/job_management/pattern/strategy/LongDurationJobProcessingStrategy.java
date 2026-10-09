package com.example.demo.job_management.pattern.strategy;

import com.example.demo.job_management.entity.MachineJob;
import org.springframework.stereotype.Component;

@Component
public class LongDurationJobProcessingStrategy implements JobProcessingStrategy {
    
    @Override
    public String processJob(MachineJob job) {
        return "LONG DURATION processing for job: " + job.getJobId() + 
               " - Extended duration handling with resource optimization - Venue: " + job.getVenue();
    }
    
    @Override
    public String getStrategyName() {
        return "LONG_DURATION_PROCESSING";
    }
}
