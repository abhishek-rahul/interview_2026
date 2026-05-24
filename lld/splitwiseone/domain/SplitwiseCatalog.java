package lld.splitwiseone.domain;

import java.util.ArrayList;
import java.util.List;

public class SplitwiseCatalog {
    final List<User> users = new ArrayList<>();
    final List<Group> groups = new ArrayList<>();

    public void addUser(User u) {
        users.add(u);
    }

    public void addGroup(Group g) {
        groups.add(g);
    }

    public User findUserById(String userId) {
        if (userId == null || userId.trim().isEmpty())
            return null;

        for (User u : users) {
            if (u.id.equals(userId))
                return u;
        }
        return null;
    }

    public Group findGroupById(String groupId) {
        if (groupId == null || groupId.trim().isEmpty())
            return null;

        for (Group g : groups) {
            if (g.id.equals(groupId))
                return g;
        }
        return null;
    }
}
