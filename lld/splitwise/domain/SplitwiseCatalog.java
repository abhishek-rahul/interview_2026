package lld.splitwise.domain;

import java.util.ArrayList;
import java.util.List;

public class SplitwiseCatalog {
    final List<User> users = new ArrayList<>();
    final List<Group> groups = new ArrayList<>();

    public void addUser(User u) { users.add(u); }
    public void addGroup(Group g) { groups.add(g); }

    User findUserById(String userId) {
        // TODO Stage 7A
        return null;
    }

    Group findGroupById(String groupId) {
        // TODO Stage 7A
        return null;
    }
}
