package com.example.demo.job_management.pattern.factory;

import com.example.demo.job_management.entity.JobStatus;

public interface JobOperationFactory {
    JobOperation createOperation(JobStatus status);
}
