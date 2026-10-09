package com.example.demo.machine_management.pattern.factory;

import com.example.demo.machine_management.entity.MachineType;

public class BackhoeOperation implements MachineOperation {
    
    @Override
    public String execute(String operation) {
        return "Backhoe performing: " + operation + " with excavation and loading";
    }
    
    @Override
    public MachineType getMachineType() {
        return MachineType.BACKHOE;
    }
}
