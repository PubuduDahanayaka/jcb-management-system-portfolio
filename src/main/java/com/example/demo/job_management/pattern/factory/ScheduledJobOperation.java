package com.example.demo.job_management.pattern.factory;

import com.example.demo.job_management.entity.JobStatus;

public class ScheduledJobOperation implements JobOperation {
    
    @Override
    public String execute(String operation) {
        return "Scheduled Job: " + operation + " - job is scheduled and ready to start";
    }
    
    @Override
    public JobStatus getJobStatus() {
        return JobStatus.SCHEDULED;
    }
}
