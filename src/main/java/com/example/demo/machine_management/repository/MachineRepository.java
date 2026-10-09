package com.example.demo.machine_management.repository;

import com.example.demo.machine_management.entity.FuelType;
import com.example.demo.machine_management.entity.Machine;
import com.example.demo.machine_management.entity.MachineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, String> {
    
    Optional<Machine> findByMachineIdAndIsActiveTrue(String machineId);
    
    List<Machine> findByMachineType(MachineType machineType);
    
    List<Machine> findByFuelType(FuelType fuelType);
    
    List<Machine> findByIsActiveTrue();
    
    @Query("SELECT m FROM Machine m WHERE m.machineType = :machineType AND m.fuelType = :fuelType AND m.isActive = true")
    List<Machine> findByMachineTypeAndFuelType(@Param("machineType") MachineType machineType, @Param("fuelType") FuelType fuelType);
    
    @Query("SELECT COUNT(m) FROM Machine m WHERE m.machineType = :machineType")
    Long countByMachineType(@Param("machineType") MachineType machineType);
    
    @Query("SELECT m FROM Machine m WHERE m.currentFuelLevel < :threshold AND m.isActive = true")
    List<Machine> findMachinesWithLowFuel(@Param("threshold") Double threshold);
    
    @Query("SELECT m FROM Machine m WHERE m.nextMaintenanceDate <= :date AND m.isActive = true")
    List<Machine> findMachinesDueForMaintenance(@Param("date") LocalDateTime date);
    
    @Query("SELECT m FROM Machine m WHERE m.operatingHours >= :hours AND m.isActive = true")
    List<Machine> findMachinesWithHighOperatingHours(@Param("hours") Double hours);
    
    boolean existsByMachineId(String machineId);
}
