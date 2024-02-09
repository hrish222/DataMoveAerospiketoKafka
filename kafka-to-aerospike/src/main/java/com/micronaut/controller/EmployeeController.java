package com.micronaut.controller;

import com.micronaut.record.EmployeeRecord;
import com.micronaut.service.EmployeeService;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.List;

@Controller("/")
public class EmployeeController {

    @Inject
    EmployeeService employeeService;

    @Get("/getemployeebyid/{empId}")
    public EmployeeRecord getEmployeeRecordById(@PathVariable int empId) {
        return employeeService.getEmployeeRecordById(empId);
    }

    @Get("/getallemployees")
    public List<EmployeeRecord> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @Post("/createemprecord")
    public void createEmployeeRecord(@Body EmployeeRecord employeeRecord) {
        employeeService.saveEmployeeRecord(employeeRecord);
    }

    @Put("/updateemprecord")
    public void updateEmpRecord(@Body EmployeeRecord employeeRecord) {
        employeeService.saveEmployeeRecord(employeeRecord);
    }

    @Delete("deleteemprecord/{empId}")
    public void deleteEmployeeRecord(@PathVariable int empId) {
        employeeService.deleteEmployeeRecord(empId);
    }

    @Get("/gettotalempcount")
    public int getTotalEmpCount() {
        return employeeService.getTotalEmpCount();
    }
}
