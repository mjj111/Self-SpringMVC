package client.contorller;

import spring.mvc.controller.AbstractController;
import client.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import spring.mvc.view.ModelAndView;


public class CreatUserController extends AbstractController {

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        User user = new User(request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));

        ModelAndView modelAndView = jsonView();
        modelAndView.addObject("user", user);

        return modelAndView;
    }
}
