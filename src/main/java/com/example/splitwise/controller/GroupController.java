package com.example.splitwise.controller;

import com.example.splitwise.entity.Group;
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
public class GroupController {
    private Map<String, Group> groupMap;


    public void createGroup(String groupName, List<User> users) {
        String id = getId();
        Group group = Group.builder().build();
        group.setId(id);
        group.setGroupName(groupName);
        group.setGroupMembers(users);
    }

    public void addUserToGroup(User user, String groupId) {
        if (groupMap.containsKey(groupId)) {
            Group group = groupMap.get(groupId);
            List<User> groupMembers = group.getGroupMembers();
            groupMembers.add(user);
            group.setGroupMembers(groupMembers);
        }
    }

    public void removeUser(User user, String groupId) {
        if (groupMap.containsKey(groupId)) {
            Group group = groupMap.get(groupId);
            List<User> groupMembers = group.getGroupMembers();
            groupMembers.remove(user);
            group.setGroupMembers(groupMembers);
        }
    }

    public void deleteGroup(String id) {
        if (groupMap.containsKey(id)) {
            groupMap.remove(id);
        }
    }

    public Group getGroupById(String id) {
        return groupMap.getOrDefault(id, null);
    }

    public List<Group> getAllGroups() {
        return (List<Group>) groupMap.values();
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }
}
