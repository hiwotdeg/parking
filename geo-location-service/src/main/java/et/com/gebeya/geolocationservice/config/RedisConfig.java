package et.com.gebeya.geolocationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {

    @Value("${redishost}")
    private String redisHost;

    @Value("${redisport}")
    private int redisPort;


    @Bean
    public Jedis jedis() {
        return new Jedis(redisHost, redisPort);
    }
}
