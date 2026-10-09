package com.example.demo.machine_management.pattern.decorator;

import com.example.demo.machine_management.pattern.factory.MachineOperation;

public class ValidationMachineOperationDecorator extends MachineOperationDecorator {
    
    public ValidationMachineOperationDecorator(MachineOperation machineOperation) {
        super(machineOperation);
    }
    
    @Override
    public String execute(String operation) {
        if (operation == null || operation.trim().isEmpty()) {
            throw new IllegalArgumentException("Machine operation cannot be null or empty");
        }
        String result = super.execute(operation);
        return result + " [VALIDATED]";
    }
}
