package et.com.gebeya.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {


    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        // Configure key serializer
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);

        // Configure value serializer
        RedisSerializer<Object> valueSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;

    }
}
