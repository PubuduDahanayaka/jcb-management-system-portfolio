package com.example.demo.machine_management.util;

import com.example.demo.machine_management.entity.MachineType;
import com.example.demo.machine_management.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MachineIdGenerator {
    
    @Autowired
    private MachineRepository machineRepository;
    
    public String generateMachineId(MachineType machineType) {
        String prefix = getPrefixForMachineType(machineType);
        Long count = machineRepository.countByMachineType(machineType);
        String sequence = String.format("%04d", count + 1);
        return prefix + sequence;
    }
    
    private String getPrefixForMachineType(MachineType machineType) {
        return switch (machineType) {
            case EXCAVATOR -> "EXC";
            case LOADER -> "LOD";
            case BULLDOZER -> "BUL";
            case CRANE -> "CRA";
            case FORKLIFT -> "FOR";
            case COMPACTOR -> "COM";
            case GRADER -> "GRA";
            case BACKHOE -> "BAC";
            case SKID_STEER -> "SKI";
            case DUMP_TRUCK -> "DUM";
        };
    }
}
