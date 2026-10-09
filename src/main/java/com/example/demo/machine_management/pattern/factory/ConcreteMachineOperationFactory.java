package com.example.demo.machine_management.pattern.factory;

import com.example.demo.machine_management.entity.MachineType;
import org.springframework.stereotype.Component;

@Component
public class ConcreteMachineOperationFactory implements MachineOperationFactory {
    
    @Override
    public MachineOperation createOperation(MachineType machineType) {
        return switch (machineType) {
            case EXCAVATOR -> new ExcavatorOperation();
            case LOADER -> new LoaderOperation();
            case BULLDOZER -> new BulldozerOperation();
            case CRANE -> new CraneOperation();
            case FORKLIFT -> new ForkliftOperation();
            case COMPACTOR -> new CompactorOperation();
            case GRADER -> new GraderOperation();
            case BACKHOE -> new BackhoeOperation();
            case SKID_STEER -> new SkidSteerOperation();
            case DUMP_TRUCK -> new DumpTruckOperation();
        };
    }
}
