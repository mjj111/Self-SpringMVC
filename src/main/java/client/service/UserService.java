package client.service;

import client.controller.dto.CreatUserDto;
import client.domain.User;
import client.domain.repsotiroy.UserRepository;
import spring.ioc.annotation.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(final CreatUserDto creatUserDto) {
        User user = User.from(creatUserDto);
        return userRepository.save(user);
    }

    public List<User> getUsersWithSize(final int wannaSize) {
        return userRepository.getUsersWithLimit(wannaSize);
    }
}
