package com.example.demo.machine_management.pattern.factory;

import com.example.demo.machine_management.entity.MachineType;

public interface MachineOperationFactory {
    MachineOperation createOperation(MachineType machineType);
}
