package org.study.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisCustomConfiguration {
    /**
     * org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration#cacheManager
     * 自动引入下列 RedisCacheConfiguration bean对象
     */
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(getObjectMapper());

        return RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(
                                              RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                                      .serializeValuesWith(
                                              RedisSerializationContext.SerializationPair.fromSerializer(
                                                      jackson2JsonRedisSerializer))
                                      .disableCachingNullValues();
    }

  /*  @Bean  LettuceConnectionFactory  引入commons-pool2 jar包的话
      已经自动使用commons-pool2配置了连接池，所以针对 CacheManager 无需再配置
    public RedisConnectionFactory redisConnectionFactory(){
    }*/

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);


        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(getObjectMapper());
        template.setValueSerializer(jackson2JsonRedisSerializer);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);

        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    private static ObjectMapper getObjectMapper() {
        // 如果直接使用Jackson2JsonRedisSerializer 获取存储的对象则会变为LinkedHashMap,添加ObjectMapper可解决
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL,
                                 JsonTypeInfo.As.PROPERTY);
        return om;
    }
}
