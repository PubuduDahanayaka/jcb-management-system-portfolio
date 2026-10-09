package com.example.demo.machine_management.pattern.factory;

import com.example.demo.machine_management.entity.MachineType;

public class SkidSteerOperation implements MachineOperation {
    
    @Override
    public String execute(String operation) {
        return "Skid Steer performing: " + operation + " with versatile operations";
    }
    
    @Override
    public MachineType getMachineType() {
        return MachineType.SKID_STEER;
    }
}
