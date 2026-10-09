package com.example.demo.machine_management.pattern.factory;

import com.example.demo.machine_management.entity.MachineType;

public class LoaderOperation implements MachineOperation {
    
    @Override
    public String execute(String operation) {
        return "Loader performing: " + operation + " with material handling capabilities";
    }
    
    @Override
    public MachineType getMachineType() {
        return MachineType.LOADER;
    }
}
