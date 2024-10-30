package com.example.pgdemo.config;

import com.example.pgdemo.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

  @Bean
  public ObjectMapper redisObjectMapper() {
    return JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .build();
  }

  @Bean
  public Jackson2JsonRedisSerializer<Event> eventSerializer(ObjectMapper redisObjectMapper) {
    Jackson2JsonRedisSerializer<Event> serializer = new Jackson2JsonRedisSerializer<>(redisObjectMapper, Event.class);
    return serializer;
  }

  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory,
                                        Jackson2JsonRedisSerializer<Event> eventSerializer) {
    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofHours(1))
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(eventSerializer));

    return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(config)
            .build();
  }

  @Bean
  public RedisTemplate<String, Event> redisTemplate(RedisConnectionFactory connectionFactory,
                                                    Jackson2JsonRedisSerializer<Event> eventSerializer) {
    RedisTemplate<String, Event> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(eventSerializer);
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(eventSerializer);
    return template;
  }
}