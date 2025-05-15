package com.example.baro_itn.domain.user.repository;

import com.example.baro_itn.domain.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    private static final AtomicLong id = new AtomicLong(1);
    private static final Map<String, User> users = new ConcurrentHashMap<>();
    private static final Map<Long, String> userIdToName = new ConcurrentHashMap<>();

    public User findByUsername(String username) {
        return users.get(username);
    }

    public User save(String username, String password, String nickname) {
        Long userId = id.getAndIncrement();
        User user = new User(
                userId,
                username,
                password,
                nickname);
        userIdToName.put(userId, username);
        users.put(username, user);
        return user;
    }

    public boolean isExists(String username) {
        return users.containsKey(username);
    }
}
