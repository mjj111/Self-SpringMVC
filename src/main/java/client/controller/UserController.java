package client.controller;

import client.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import spring.mvc.annotation.Controller;
import spring.mvc.annotation.RequestMapping;
import spring.mvc.controller.AbstractController;
import spring.mvc.handler.mapping.RequestMethod;
import spring.mvc.view.ModelAndView;

@Controller
public class UserController extends AbstractController {

    @RequestMapping("/users")
    public ModelAndView getUsers(HttpServletRequest request, HttpServletResponse response) {
        int wannaSize = Integer.parseInt(request.getParameter("size"));

        ModelAndView modelAndView = jsonView();
        for(int i = 0; i < wannaSize; i++) {
            User newUser = new User(String.valueOf(i),
                    "tmpPassword",
                    i + "김명준",
                    "skatks1016@gmail.com");

            modelAndView.addObject("user"+ i, newUser);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));

        ModelAndView modelAndView = jsonView();
        modelAndView.addObject("user", user);

        return modelAndView;
    }
}
