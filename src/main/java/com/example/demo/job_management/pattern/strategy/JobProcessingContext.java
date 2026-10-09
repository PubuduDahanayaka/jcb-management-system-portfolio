package com.example.demo.job_management.pattern.strategy;

import com.example.demo.job_management.entity.JobStatus;
import com.example.demo.job_management.entity.MachineJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobProcessingContext {
    
    @Autowired
    private StandardJobProcessingStrategy standardStrategy;
    
    @Autowired
    private UrgentJobProcessingStrategy urgentStrategy;
    
    @Autowired
    private LongDurationJobProcessingStrategy longDurationStrategy;
    
    @Autowired
    private CompletedJobProcessingStrategy completedStrategy;
    
    private JobProcessingStrategy strategy;
    
    public void setStrategy(JobProcessingStrategy strategy) {
        this.strategy = strategy;
    }
    
    public String processJob(MachineJob job) {
        if (strategy == null) {
            strategy = selectStrategyByJobStatus(job.getStatus());
        }
        return strategy.processJob(job);
    }
    
    private JobProcessingStrategy selectStrategyByJobStatus(JobStatus status) {
        return switch (status) {
            case COMPLETED -> completedStrategy;
            case OVERDUE -> urgentStrategy;
            case IN_PROGRESS -> standardStrategy;
            default -> standardStrategy;
        };
    }
    
    public String processJobWithStrategy(MachineJob job) {
        JobProcessingStrategy selectedStrategy = selectStrategyByJobStatus(job.getStatus());
        
        if (job.getStatus() == JobStatus.IN_PROGRESS && jobHasLongDuration(job)) {
            selectedStrategy = longDurationStrategy;
        }
        
        return selectedStrategy.processJob(job);
    }
    
    private boolean jobHasLongDuration(MachineJob job) {
        return job.getEstimatedDurationHours() != null && job.getEstimatedDurationHours() > 8.0;
    }
    
    public String getCurrentStrategyName() {
        return strategy != null ? strategy.getStrategyName() : "NO_STRATEGY_SELECTED";
    }
}
