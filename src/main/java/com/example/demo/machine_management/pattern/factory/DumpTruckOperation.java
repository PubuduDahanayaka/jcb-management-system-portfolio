package com.example.demo.machine_management.pattern.factory;

import com.example.demo.machine_management.entity.MachineType;

public class DumpTruckOperation implements MachineOperation {
    
    @Override
    public String execute(String operation) {
        return "Dump Truck performing: " + operation + " with material transport";
    }
    
    @Override
    public MachineType getMachineType() {
        return MachineType.DUMP_TRUCK;
    }
}
