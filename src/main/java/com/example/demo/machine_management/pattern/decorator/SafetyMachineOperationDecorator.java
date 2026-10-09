package com.example.demo.machine_management.pattern.decorator;

import com.example.demo.machine_management.pattern.factory.MachineOperation;

public class SafetyMachineOperationDecorator extends MachineOperationDecorator {
    
    public SafetyMachineOperationDecorator(MachineOperation machineOperation) {
        super(machineOperation);
    }
    
    @Override
    public String execute(String operation) {
        String result = super.execute(operation);
        System.out.println("Safety Check: " + getMachineType() + " operation safety verified");
        return result + " [SAFETY_CHECKED]";
    }
}
