package io.github.zhiweicoding.csw.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Created by zhiwei on 2022/4/4.
 */
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        //default信息缓存配置
        RedisCacheConfiguration default1h = RedisCacheConfiguration.defaultCacheConfig()
                // 设置过期时间
                .entryTtl(Duration.ofHours(1))
                // String的方式序列化key
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
                // jackson的方式序列化value
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer()))
                // 空值不缓存
                .disableCachingNullValues()
                // 设置缓存名称前缀
                .prefixCacheNameWith("default_1h:");
        RedisCacheConfiguration m30 = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixCacheNameWith("srw_");
        RedisCacheConfiguration m15 = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(15)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixCacheNameWith("srw_");
        RedisCacheConfiguration d30 = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(30)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixCacheNameWith("srw_");
        RedisCacheConfiguration h24 = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(24)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixCacheNameWith("srw_");
        RedisCacheConfiguration s60 = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(60)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixCacheNameWith("srw_");
        RedisCacheConfiguration s30 = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(30)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer())).disableCachingNullValues().prefixCacheNameWith("srw_");

        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new LinkedHashMap<String, RedisCacheConfiguration>(8) {
            private static final long serialVersionUID = 2474073019770319870L;

            {
                put("60s", s60);
                put("30s", s30);
                put("15m", m15);
                put("30m", m30);
                put("24h", h24);
                put("30d", d30);
                put("default", default1h);
            }
        };

        return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory), default1h, redisCacheConfigurationMap);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        RedisSerializer<Object> jackson2JsonRedisSerializer = jsonSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    private RedisSerializer<Object> jsonSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        // 解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

}
