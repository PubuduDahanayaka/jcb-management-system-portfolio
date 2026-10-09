package com.example.demo.machine_management.pattern.factory;

import com.example.demo.machine_management.entity.MachineType;

public class CompactorOperation implements MachineOperation {
    
    @Override
    public String execute(String operation) {
        return "Compactor performing: " + operation + " with soil compaction";
    }
    
    @Override
    public MachineType getMachineType() {
        return MachineType.COMPACTOR;
    }
}
