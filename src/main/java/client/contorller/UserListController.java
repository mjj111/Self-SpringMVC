package client.contorller;

import client.model.User;
import spring.mvc.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import spring.mvc.view.ModelAndView;


public class UserListController extends AbstractController {

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
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
}
