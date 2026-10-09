package com.example.demo.machine_management.pattern.factory;

import com.example.demo.machine_management.entity.MachineType;

public class ForkliftOperation implements MachineOperation {
    
    @Override
    public String execute(String operation) {
        return "Forklift performing: " + operation + " with warehouse operations";
    }
    
    @Override
    public MachineType getMachineType() {
        return MachineType.FORKLIFT;
    }
}
