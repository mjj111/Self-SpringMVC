package http.controller;

import http.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.view.request.HttpRequest;
import http.view.response.HttpResponse;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public void doPost(final HttpRequest request, final HttpResponse response) {
        User user = new User(request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        String result = user.toString();

        response.sendResponse(result);
    }
}
