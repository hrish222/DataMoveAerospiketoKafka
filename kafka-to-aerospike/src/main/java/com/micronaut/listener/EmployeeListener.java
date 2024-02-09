package com.micronaut.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micronaut.constant.KafkaConstants;
import com.micronaut.record.EmployeeRecord;
import com.micronaut.service.EmployeeService;
import com.micronaut.util.DateUtil;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.OffsetStrategy;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@KafkaListener(offsetReset = OffsetReset.LATEST, offsetStrategy = OffsetStrategy.AUTO)
public class EmployeeListener {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeListener.class);

    @Inject
    DateUtil dateUtil;

    @Inject
    EmployeeService employeeService;

    @Topic(KafkaConstants.TOPICNAME)
    public void receive(ConsumerRecord<String, String>record, long offset) {
        String jsonReq = record.value();

        if (jsonReq == null) {
            logger.info("Received null record from "+KafkaConstants.TOPICNAME+" with offset: "+offset);
            return;
        }
        logger.info("Received message from "+KafkaConstants.TOPICNAME+" "+jsonReq);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonReq);

            String op = jsonNode.get("op").asText();

            if (op.equals("i") || op.equals("u")) {
                String employeeJson = jsonNode.get("value").toString();

                int empId = jsonNode.get("id").asInt();
                long startDate = jsonNode.get("value").get("START_DT").asLong();
                long endDate = jsonNode.get("value").get("END_DT").asLong();

                if (employeeJson == null) {
                    logger.info("Received null after field from "+KafkaConstants.TOPICNAME+" with offset: "+offset);
                }
                EmployeeRecord employeeRecord = objectMapper.readValue(employeeJson, EmployeeRecord.class);
                employeeRecord.setEmpId(empId);
                employeeRecord.setStartDate(dateUtil.getFormattedDate(startDate));
                employeeRecord.setEndDate(dateUtil.getFormattedDate(endDate));

                employeeService.saveEmployeeRecord(employeeRecord);
            }
            else if (op.equals("d")) {
                String resJson = jsonNode.get("value").toString();
                EmployeeRecord employeeRecord = objectMapper.readValue(resJson, EmployeeRecord.class);
                employeeService.deleteEmployeeRecord(employeeRecord.getEmpId());
            }
        }
        catch (NullPointerException e) {
            logger.error("error in json conversion from kafka message, VEHICLE json "+jsonReq + e);
        }
        catch (JsonProcessingException e) {
            logger.error("error in json processing from kafka message, VEHICLE json "+jsonReq + e);
        }
    }
}
