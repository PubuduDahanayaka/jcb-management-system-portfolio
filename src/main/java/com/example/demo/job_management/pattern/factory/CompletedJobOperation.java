package com.example.demo.job_management.pattern.factory;

import com.example.demo.job_management.entity.JobStatus;

public class CompletedJobOperation implements JobOperation {
    
    @Override
    public String execute(String operation) {
        return "Completed Job: " + operation + " - job has been successfully completed";
    }
    
    @Override
    public JobStatus getJobStatus() {
        return JobStatus.COMPLETED;
    }
}
