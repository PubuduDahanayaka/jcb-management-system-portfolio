package com.example.demo.job_management.pattern.strategy;

import com.example.demo.job_management.entity.MachineJob;

public interface JobProcessingStrategy {
    String processJob(MachineJob job);
    String getStrategyName();
}
