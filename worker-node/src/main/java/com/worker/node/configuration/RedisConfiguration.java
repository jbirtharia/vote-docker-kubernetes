package com.worker.node.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

        @Bean
        public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {

            RedisTemplate redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(connectionFactory);

            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
            redisTemplate.afterPropertiesSet();
            return redisTemplate;
        }
}
