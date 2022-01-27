package learn.redis.publisher;

import learn.model.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RedisMessagePublisher {
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisMessagePublisher(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(Config config) {
        List<String> subdomains = config.getSubdomainUrls();
        redisTemplate.opsForList().leftPushAll("subdomains", subdomains);
        config.getCookies().forEach(cookie -> {
            String key = "cookie:" + cookie.getName() + ":" + cookie.getHost();
            redisTemplate.opsForList().leftPush(key, cookie.getData());
        });
        log.info("subdomains in redis {}", redisTemplate.opsForList().range("subdomains", 0, -1));
    }
}
