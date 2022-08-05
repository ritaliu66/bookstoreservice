package com.epam.bookstoreservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {


    @Bean
    public RedisTemplate getRedisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    public RedisConnectionFactory redisConnectionFactory() {
        return new RedisConnectionFactory() {
            @Override
            public DataAccessException translateExceptionIfPossible(RuntimeException e) {
                return null;
            }

            @Override
            public RedisConnection getConnection() {
                return null;
            }

            @Override
            public RedisClusterConnection getClusterConnection() {
                return null;
            }

            @Override
            public boolean getConvertPipelineAndTxResults() {
                return false;
            }

            @Override
            public RedisSentinelConnection getSentinelConnection() {
                return null;
            }
        };
    }

}
