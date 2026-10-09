package com.example.demo.machine_management.pattern.factory;

import com.example.demo.machine_management.entity.MachineType;

public class BulldozerOperation implements MachineOperation {
    
    @Override
    public String execute(String operation) {
        return "Bulldozer performing: " + operation + " with earthmoving capabilities";
    }
    
    @Override
    public MachineType getMachineType() {
        return MachineType.BULLDOZER;
    }
}
