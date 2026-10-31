package org.example.canal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

@CanalTable("user_info")
@Component
public class UserHandler implements EntryHandler<User> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void insert(User user) {
        // 写数据到redis
        saveUser(user);
    }

    @Override
    public void update(User before, User after) {
        // 写数据到redis
        saveUser(after);
    }

    @Override
    public void delete(User user) {
        // 删除数据到redis
        deleteUserById(user.getId());
    }

    public void saveUser(User user) {
        try {
            String json = MAPPER.writeValueAsString(user);
            redisTemplate.opsForValue().set("user:id:" + user.getId(), json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUserById(Long id) {
        redisTemplate.delete("user:id:" + id);
    }
}
