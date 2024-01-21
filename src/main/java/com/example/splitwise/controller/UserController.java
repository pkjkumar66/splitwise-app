package com.example.splitwise.controller;

import com.example.splitwise.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserController {
    private Map<String, User> userMap;

    public void addUser(User user) {
        String id = getId();
        user.setId(id);
        userMap.put(id, user);
    }

    public void updateUser(User user) {
        User existingUser = userMap.get(user.getId());
        userMap.replace(existingUser.getId(), user);
    }

    public void deleteUser(String id) {
        if (userMap.containsKey(id)) {
            userMap.remove(id);
        }
    }

    public User getUserById(String id) {
        return userMap.getOrDefault(id, null);
    }

    public List<User> getUserList() {
        return (List<User>) userMap.values();
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }
}
