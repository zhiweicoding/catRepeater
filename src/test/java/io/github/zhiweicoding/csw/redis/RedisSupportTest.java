package io.github.zhiweicoding.csw.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @Created by zhiwei on 2022/4/5.
 */
@SpringBootTest
public class RedisSupportTest {

    @Resource
    private RedisTemplate<Serializable, Object> redisTemplate;

    @Test
    public void test() {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        operations.set("testKey","123");
    }

    @Test
    public void test2() {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        operations.set("testKey2","123");
        redisTemplate.expire("testKey2",1, TimeUnit.HOURS);
    }
}
