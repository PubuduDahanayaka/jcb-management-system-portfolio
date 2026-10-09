package com.example.demo.job_management.pattern.factory;

import com.example.demo.job_management.entity.JobStatus;

public class OverdueJobOperation implements JobOperation {
    
    @Override
    public String execute(String operation) {
        return "Overdue Job: " + operation + " - job is past its scheduled end time";
    }
    
    @Override
    public JobStatus getJobStatus() {
        return JobStatus.OVERDUE;
    }
}
