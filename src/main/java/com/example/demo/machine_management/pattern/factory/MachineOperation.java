package com.example.demo.machine_management.pattern.factory;

import com.example.demo.machine_management.entity.MachineType;

public interface MachineOperation {
    String execute(String operation);
    MachineType getMachineType();
}
