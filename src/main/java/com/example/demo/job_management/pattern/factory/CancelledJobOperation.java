package com.example.demo.job_management.pattern.factory;

import com.example.demo.job_management.entity.JobStatus;

public class CancelledJobOperation implements JobOperation {
    
    @Override
    public String execute(String operation) {
        return "Cancelled Job: " + operation + " - job has been cancelled";
    }
    
    @Override
    public JobStatus getJobStatus() {
        return JobStatus.CANCELLED;
    }
}
