package com.example.demo.machine_management.pattern.decorator;

import com.example.demo.machine_management.pattern.factory.MachineOperation;

public abstract class MachineOperationDecorator implements MachineOperation {
    
    protected MachineOperation machineOperation;
    
    public MachineOperationDecorator(MachineOperation machineOperation) {
        this.machineOperation = machineOperation;
    }
    
    @Override
    public String execute(String operation) {
        return machineOperation.execute(operation);
    }
    
    @Override
    public com.example.demo.machine_management.entity.MachineType getMachineType() {
        return machineOperation.getMachineType();
    }
}
