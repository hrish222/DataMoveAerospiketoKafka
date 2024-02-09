package com.micronaut.config;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.async.EventLoops;
import com.aerospike.client.async.EventPolicy;
import com.aerospike.client.async.NioEventLoops;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.mapper.tools.AeroMapper;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Factory
public class AerospikeConfig {
    private static final int maxCommandsInProcess = 40;
    private static final int eventLoopSize = Math.min(Runtime.getRuntime().availableProcessors(), 8);
    private static final int writeTimeout = 5000;

    @Singleton
    public AerospikeClient aerospikeClient(AerospikeProperties aerospikeProperties) {
        final ClientPolicy clientPolicy = new ClientPolicy();
        String hosts = String.join(",", aerospikeProperties.getHost());
        return new AerospikeClient(clientPolicy, Host.parseHosts(hosts, aerospikeProperties.getPort()));
    }

    @Singleton
    public AeroMapper aeroMapper(AerospikeClient aerospikeClient) {
        return new AeroMapper.Builder(aerospikeClient).build();
    }

    @Bean(preDestroy = "close")
    @Singleton
    public EventLoops nioEventLoops() {
        EventPolicy eventPolicy = new EventPolicy();
        eventPolicy.maxCommandsInProcess = maxCommandsInProcess;
        eventPolicy.maxCommandsInQueue = 60;
        eventPolicy.minTimeout = writeTimeout;

        return new NioEventLoops(eventPolicy, eventLoopSize);
    }
}
