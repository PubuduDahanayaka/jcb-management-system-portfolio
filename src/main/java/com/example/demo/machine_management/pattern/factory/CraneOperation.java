package com.example.demo.machine_management.pattern.factory;

import com.example.demo.machine_management.entity.MachineType;

public class CraneOperation implements MachineOperation {
    
    @Override
    public String execute(String operation) {
        return "Crane performing: " + operation + " with lifting capabilities";
    }
    
    @Override
    public MachineType getMachineType() {
        return MachineType.CRANE;
    }
}
