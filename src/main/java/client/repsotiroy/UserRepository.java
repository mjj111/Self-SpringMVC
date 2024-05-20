package client.repsotiroy;

import client.model.User;
import spring.mvc.annotation.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<Long, User> temporaryDatabase = new HashMap<>();
    private Long SEQUENCE = 0L;

    public void save(final User user) {
        temporaryDatabase.put(SEQUENCE++,user);
    }

    public List<User> getUsersWithLimit(int wannaSize) {
        List<User> allUsers = (List<User>) temporaryDatabase.values();

        return allUsers.stream()
                .limit(wannaSize)
                .toList();
    }
}
