package et.com.gebeya.geolocationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {

//    @Value("${spring.redis.host}")
    private String redisHost = "localhost";

//    @Value("${spring.redis.port}")
    private int redisPort = 6379;



    @Bean
    public Jedis jedis() {
        return new Jedis(redisHost, redisPort);
    }
}
