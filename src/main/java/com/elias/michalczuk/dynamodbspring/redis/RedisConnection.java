package com.elias.michalczuk.dynamodbspring.redis;

import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import com.elias.michalczuk.dynamodbspring.product.repository.RedisProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
class RedisConfiguration {

    @Autowired
    RedisProperties redisProperties;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return new LettuceConnectionFactory(LettuceConnectionFactory.createRedisConfiguration(redisProperties.uri));
    }

    @Bean
    public ReactiveRedisTemplate<String, RedisProduct> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<RedisProduct> valueSerializer =
                new Jackson2JsonRedisSerializer<>(RedisProduct.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, RedisProduct> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, RedisProduct> context =
                builder.value(valueSerializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }
}