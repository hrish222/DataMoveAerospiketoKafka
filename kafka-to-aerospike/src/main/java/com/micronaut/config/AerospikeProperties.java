package com.micronaut.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

import java.util.List;

@Data
@ConfigurationProperties("aerospike")
public class AerospikeProperties {

    private List<String> host;
    private Integer port;
    private String namespace;
}
