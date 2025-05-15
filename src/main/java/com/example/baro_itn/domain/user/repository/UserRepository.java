package com.example.baro_itn.domain.user.repository;

import com.example.baro_itn.common.enums.RoleType;
import com.example.baro_itn.domain.user.entity.User;
import com.example.baro_itn.domain.user.enums.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    private final PasswordEncoder passwordEncoder;
    private static final AtomicLong id = new AtomicLong(1);
    private static final Map<String, User> users = new ConcurrentHashMap<>();
    private static final Map<Long, String> userIdToName = new ConcurrentHashMap<>();

    public UserRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

        Map<RoleType, UserRole> userRole = new HashMap<>();
        userRole.put(RoleType.USER, UserRole.ADMIN);

        User adminUser = new User(
                0L,
                "admin",
                passwordEncoder.encode("admin"),
                "adminnick",
                userRole
        );

        userIdToName.put(adminUser.getId(), adminUser.getUsername());
        users.put(adminUser.getUsername(), adminUser);
    }

    public User findByUserId(Long userId) {
        return users.get(userIdToName.get(userId));
    }

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

    public void update(User user) {
        users.put(user.getUsername(), user);
    }

    public boolean isExists(String username) {
        return users.containsKey(username);
    }
}
