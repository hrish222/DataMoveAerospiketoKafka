package com.micronaut.record;

import com.aerospike.mapper.annotations.AerospikeBin;
import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.micronaut.constant.AerospikeConstants;
import com.micronaut.constant.JsonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@AerospikeRecord(namespace = AerospikeConstants.NAMESPACE, set = AerospikeConstants.SETS.EMPLOYEE)
public class EmployeeRecord {

    @AerospikeKey
//    @JsonProperty(JsonConstants.VEHICLE_JSON.EMP_ID)//employee_json
    @AerospikeBin(name = AerospikeConstants.EMPLOYEE_BINS.EMP_ID)
    private int empId;

    @JsonProperty(JsonConstants.VEHICLE_JSON.EMP_NAME)
    @AerospikeBin(name = AerospikeConstants.EMPLOYEE_BINS.EMP_NAME)
    private String empName;

    @JsonProperty(JsonConstants.VEHICLE_JSON.DEPARTMENT)
    @AerospikeBin(name = AerospikeConstants.EMPLOYEE_BINS.DEPARTMENT)
    private String department;

//    @JsonProperty(JsonConstants.VEHICLE_JSON.START_DT)
    @AerospikeBin(name = AerospikeConstants.EMPLOYEE_BINS.START_DT)
    private String startDate;

//    @JsonProperty(JsonConstants.VEHICLE_JSON.END_DT)
    @AerospikeBin(name = AerospikeConstants.EMPLOYEE_BINS.END_DT)
    private String endDate;

    @JsonProperty(JsonConstants.VEHICLE_JSON.UPDATED_BY)
    @AerospikeBin(name = AerospikeConstants.EMPLOYEE_BINS.UPDATED_BY)
    private String updatedBy;

    @JsonProperty(JsonConstants.VEHICLE_JSON.ACTION)
    @AerospikeBin(name = AerospikeConstants.EMPLOYEE_BINS.ACTION)
    private String action;
}
