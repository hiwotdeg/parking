package et.com.gebeya.notificationservice.service;

import et.com.gebeya.notificationservice.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Long> redisTemplate;
    private final RedisTemplate<String, MessageDto> messageDtoRedisTemplate;

    public void setChatId(String key, Long value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Long getChatId(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteChatId(String key) {
        redisTemplate.delete(key);
    }


    public void setMessage(String key,MessageDto value){
        messageDtoRedisTemplate.opsForValue().set(key, value);
    }
    public MessageDto getMessage(String key){
        return messageDtoRedisTemplate.opsForValue().get(key);
    }
    public void deleteKey(String key){
        messageDtoRedisTemplate.delete(key);
    }
}
