package com.example.demo.job_management.pattern.factory;

import com.example.demo.job_management.entity.JobStatus;

public class PausedJobOperation implements JobOperation {
    
    @Override
    public String execute(String operation) {
        return "Paused Job: " + operation + " - job execution has been paused";
    }
    
    @Override
    public JobStatus getJobStatus() {
        return JobStatus.PAUSED;
    }
}
