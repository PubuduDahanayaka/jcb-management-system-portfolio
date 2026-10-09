package com.example.demo.job_management.pattern.factory;

import com.example.demo.job_management.entity.JobStatus;

public class OnHoldJobOperation implements JobOperation {
    
    @Override
    public String execute(String operation) {
        return "On Hold Job: " + operation + " - job is temporarily on hold";
    }
    
    @Override
    public JobStatus getJobStatus() {
        return JobStatus.ON_HOLD;
    }
}
