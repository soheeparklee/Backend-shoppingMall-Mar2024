package org.shoppingMall.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisUtil {
    private final StringRedisTemplate redisTemplate; // Redis에 접근하기 위한 Spring의 Redis 탬플릿 클래스

    public String getData(String key){ // 지정된 key에 해당하는 데이터를 Redis에서 가져오는 메서드
        ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setData(String key, String value){ // 지정된 key에 값을 저장하는 메서드
        ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void setDataExpire(String key, String value, long duration){ // 지정된 key에 값을 지정하고, 지정된 시간 후에 데이터가 만료되도록 설정하는 메서드
        ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();
        Duration expireDuration= Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key){ // 지정된 key 에 해당하는 데이터를 Redis에서 삭제하는 메서드
        redisTemplate.delete(key);
    }

}
