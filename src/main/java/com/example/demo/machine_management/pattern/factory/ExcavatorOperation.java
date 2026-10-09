package com.example.demo.machine_management.pattern.factory;

import com.example.demo.machine_management.entity.MachineType;

public class ExcavatorOperation implements MachineOperation {
    
    @Override
    public String execute(String operation) {
        return "Excavator performing: " + operation + " with digging capabilities";
    }
    
    @Override
    public MachineType getMachineType() {
        return MachineType.EXCAVATOR;
    }
}
