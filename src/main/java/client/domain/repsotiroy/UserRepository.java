package client.domain.repsotiroy;

import client.controller.dto.CreatUserDto;
import client.domain.User;
import spring.ioc.annotation.Repository;
import java.util.*;

@Repository
public class UserRepository {

    private final Map<Long, User> temporaryDatabase = new HashMap<>();
    private Long SEQUENCE = 0L;

    public User save(User user) {
        long id = SEQUENCE++;
        user.updateUserId(id);
        temporaryDatabase.put(id, user);
        return user;
    }

    public List<User> getUsersWithLimit(int wannaSize) {
        while(temporaryDatabase.size() < wannaSize) {
            fixUser();
        }
        return new ArrayList<>(temporaryDatabase.values());
    }

    private void fixUser() {
        CreatUserDto dto = new CreatUserDto("김명준", "비밀번호", "skatks1016@gmail.com");
        save(User.from(dto));
    }
}
