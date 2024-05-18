package spring.contorller;

import spring.mvc.Controller;
import spring.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CreatUserController implements Controller {

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        User user = new User(request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        String result = user.toString();
        return result;
    }
}
