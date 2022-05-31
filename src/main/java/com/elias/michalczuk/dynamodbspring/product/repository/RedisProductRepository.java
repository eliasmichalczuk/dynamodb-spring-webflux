package com.elias.michalczuk.dynamodbspring.product.repository;

import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RedisProductRepository {
    @Autowired
    private ReactiveRedisTemplate<String, RedisProduct> redisTemplate;
    @Bean
    public ReactiveValueOperations<String, RedisProduct> reactiveValueOps() {
        return redisTemplate.opsForValue();
    }
}