package com.elias.michalczuk.dynamodbspring.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {
    @Value( "${redis.network}" )
    public String network;
    @Value( "${redis.port}" )
    public String port;
}
