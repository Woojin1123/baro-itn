package com.example.baro_itn.domain.user.repository;

import com.example.baro_itn.domain.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    private static final AtomicLong id = new AtomicLong(1);
    private static final Map<AtomicLong, User> users = new ConcurrentHashMap<>();

    public User findById(String username) {
        return users.get(username);
    }

    public void save(User user) {
        long userId = id.getAndIncrement();

        users.put(user.getUsername(), user);
    }

    public boolean isExists(String username) {
        return users.containsKey(username);
    }
}
