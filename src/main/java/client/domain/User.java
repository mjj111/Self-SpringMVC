package client.domain;

import client.controller.dto.CreatUserDto;

public class User {
    private long userId;
    private String name;
    private String password;
    private String email;

    public User(long userId, String name, String password, String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public void updateUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public static User from(final CreatUserDto dto) {
        return new User(0, dto.name(), dto.password(), dto.email());
    }
}
