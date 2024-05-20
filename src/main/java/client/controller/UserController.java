package client.controller;

import client.controller.dto.CreatUserDto;
import client.model.User;
import client.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import spring.mvc.annotation.Controller;
import spring.mvc.annotation.RequestMapping;
import spring.mvc.controller.AbstractController;
import spring.mvc.handler.mapping.RequestMethod;
import spring.mvc.view.ModelAndView;

import java.util.List;

@Controller
public class UserController extends AbstractController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/users")
    public ModelAndView getUsers(HttpServletRequest request, HttpServletResponse response) {
        int wannaSize = Integer.parseInt(request.getParameter("size"));
        List<User> users = userService.getUsersWithSize(wannaSize);

        ModelAndView modelAndView = jsonView();
        for(int i = 0; i < wannaSize; i++) {
            User user = users.get(i);
            modelAndView.addObject("user"+ i, user);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        CreatUserDto requestDto = CreatUserDto.of(request);
        User user = userService.createUser(requestDto);

        ModelAndView modelAndView = jsonView();
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
