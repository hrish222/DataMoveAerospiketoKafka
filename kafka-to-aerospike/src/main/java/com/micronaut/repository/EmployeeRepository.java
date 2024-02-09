package com.micronaut.repository;

import com.aerospike.mapper.tools.AeroMapper;
import com.micronaut.record.EmployeeRecord;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class EmployeeRepository {

    @Inject
    AeroMapper aeroMapper;

    public void saveEmployeeRecord(EmployeeRecord employeeRecord) {
        aeroMapper.save(employeeRecord);
    }

    public void deleteEmployeeRecord(int vehicleId) {
        aeroMapper.delete(EmployeeRecord.class, vehicleId);
    }

    public EmployeeRecord getEmployeeRecordById(int empId) {
        return aeroMapper.read(EmployeeRecord.class, empId);
    }

    public List<EmployeeRecord> getAllEmployees() {
        return aeroMapper.scan(EmployeeRecord.class);
    }

    public int getTotalEmpCount() {
        return getAllEmployees().stream().collect(Collectors.toList()).size();
    }
}
