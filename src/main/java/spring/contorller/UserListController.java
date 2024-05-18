package spring.contorller;

import spring.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserListController implements Controller {

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        int wannaSize = Integer.parseInt(request.getParameter("size"));
        String result = "You want to read user information of size " + wannaSize + ".";

        return result;
    }
}
