package com.example.demo.job_management.pattern.factory;

import com.example.demo.job_management.entity.JobStatus;
import org.springframework.stereotype.Component;

@Component
public class ConcreteJobOperationFactory implements JobOperationFactory {
    
    @Override
    public JobOperation createOperation(JobStatus status) {
        return switch (status) {
            case SCHEDULED -> new ScheduledJobOperation();
            case IN_PROGRESS -> new InProgressJobOperation();
            case COMPLETED -> new CompletedJobOperation();
            case CANCELLED -> new CancelledJobOperation();
            case ON_HOLD -> new OnHoldJobOperation();
            case OVERDUE -> new OverdueJobOperation();
            case PAUSED -> new PausedJobOperation();
        };
    }
}
