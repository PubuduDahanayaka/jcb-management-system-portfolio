package com.example.demo.job_management.pattern.factory;

import com.example.demo.job_management.entity.JobStatus;

public class InProgressJobOperation implements JobOperation {
    
    @Override
    public String execute(String operation) {
        return "In Progress Job: " + operation + " - job is currently being executed";
    }
    
    @Override
    public JobStatus getJobStatus() {
        return JobStatus.IN_PROGRESS;
    }
}
