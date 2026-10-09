package com.example.demo.machine_management.pattern.decorator;

import com.example.demo.machine_management.pattern.factory.MachineOperation;

public class LoggingMachineOperationDecorator extends MachineOperationDecorator {
    
    public LoggingMachineOperationDecorator(MachineOperation machineOperation) {
        super(machineOperation);
    }
    
    @Override
    public String execute(String operation) {
        String result = super.execute(operation);
        System.out.println("Machine Logging: " + getMachineType() + " executed operation: " + operation);
        return result + " [LOGGED]";
    }
}
