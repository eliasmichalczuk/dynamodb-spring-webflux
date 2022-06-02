package com.elias.michalczuk.dynamodbspring.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {
    @Value( "${redis.uri}" )
    public String uri;
}
