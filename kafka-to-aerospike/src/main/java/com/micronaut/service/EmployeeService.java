package com.micronaut.service;

import com.micronaut.record.EmployeeRecord;
import com.micronaut.repository.EmployeeRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class EmployeeService {

    @Inject
    EmployeeRepository employeeRepository;

    public void saveEmployeeRecord(EmployeeRecord employeeRecord) {
        employeeRepository.saveEmployeeRecord(employeeRecord);
    }

    public void deleteEmployeeRecord(int vehicleId) {
        employeeRepository.deleteEmployeeRecord(vehicleId);
    }

    public EmployeeRecord getEmployeeRecordById(int empId) {
        return employeeRepository.getEmployeeRecordById(empId);
    }

    public List<EmployeeRecord> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    public int getTotalEmpCount() {
        return employeeRepository.getTotalEmpCount();
    }
}
