package com.example.demo.machine_management.pattern.factory;

import com.example.demo.machine_management.entity.MachineType;

public class GraderOperation implements MachineOperation {
    
    @Override
    public String execute(String operation) {
        return "Grader performing: " + operation + " with surface grading";
    }
    
    @Override
    public MachineType getMachineType() {
        return MachineType.GRADER;
    }
}
