package et.com.gebeya.authservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import et.com.gebeya.authservice.dto.request_dto.UsersCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void setObject(String key, UsersCredential usersCredential) {
        redisTemplate.opsForValue().set(key, usersCredential);
    }

public UsersCredential getObject(String key) {
    Object serializedObject = redisTemplate.opsForValue().get(key);
    ObjectMapper objectMapper = new ObjectMapper();
    UsersCredential userCredential = null;
    try {
        userCredential = objectMapper.convertValue(serializedObject, UsersCredential.class);
    } catch (Exception e) {
        e.printStackTrace();
    }

    return userCredential;
}

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }


}
