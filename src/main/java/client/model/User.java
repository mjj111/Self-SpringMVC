package client.model;

import client.controller.dto.CreatUserDto;

public class User {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(final String userId,
                final String password,
                final String name,
                final String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User from(final CreatUserDto creatUserDto) {
        return new User(creatUserDto.userId(),
                creatUserDto.password(),
                creatUserDto.name(),
                creatUserDto.email());
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "\n----User----\n" +
                "userId = " + userId + "\n" +
                "password = " + password + "\n" +
                "name = " + name + "\n" +
                "email = " + email+ "\n";
    }
}
