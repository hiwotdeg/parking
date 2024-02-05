package et.com.gebeya.notificationservice.telegram;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Long> redisTemplate;

    public void setChatId(String key, Long value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Long getChatId(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteChatId(String key) {
        redisTemplate.delete(key);
    }
}
