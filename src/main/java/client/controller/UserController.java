package client.controller;

import client.controller.dto.CreatUserDto;
import client.domain.User;
import client.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import spring.mvc.annotation.Controller;
import spring.mvc.annotation.RequestMapping;
import spring.mvc.web.handler.mapping.RequestMethod;
import spring.mvc.view.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        CreatUserDto requestDto = CreatUserDto.of(request);
        User user = userService.createUser(requestDto);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView getUsers(HttpServletRequest request, HttpServletResponse response) {
        int wannaSize = Integer.parseInt(request.getParameter("size"));
        List<User> users = userService.getUsersWithSize(wannaSize);

        ModelAndView modelAndView = new ModelAndView();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            modelAndView.addObject("user"+ i, user);
        }
        return modelAndView;
    }
}
