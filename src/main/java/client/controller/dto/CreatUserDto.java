package client.controller.dto;

import jakarta.servlet.http.HttpServletRequest;

public record CreatUserDto(String name,
                           String password,
                           String email,
                           String userId) {
    public static CreatUserDto of(final HttpServletRequest request) {
        return new CreatUserDto(request.getParameter("name"),
                request.getParameter("password"),
                request.getParameter("email"),
                request.getParameter("userId"));
    }
}
