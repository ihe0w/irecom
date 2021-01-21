package com.example.business_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableCaching
public class RedisConfig {
    @Value("${cache.default.expire-time}")
    private int defaultExpireTime;
    @Value("${cache.user.expire-time}")
    private int userCacheExpireTime;
    @Value("${cache.user.name}")
    private String userCacheName;

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory){
        RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig();

        // set expire time
        defaultCacheConfig=defaultCacheConfig.entryTtl(Duration.ofSeconds(defaultExpireTime))
                                                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                                                .disableCachingNullValues();

        Set<String> cacheNames=new HashSet<>();
        cacheNames.add(userCacheName);

        Map<String,RedisCacheConfiguration> configMap= new HashMap<>();
        configMap.put(userCacheName,defaultCacheConfig.entryTtl(Duration.ofSeconds(userCacheExpireTime)));

        RedisCacheManager cacheManager=RedisCacheManager.builder(factory)
                .cacheDefaults(defaultCacheConfig)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();

        return cacheManager;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory factory){
        RedisTemplate<String, String> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory connectionFactory){
        RedisTemplate<String,Serializable> template= new RedisTemplate<String,Serializable>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
