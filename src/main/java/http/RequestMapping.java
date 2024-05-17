package http;

import http.controller.Controller;
import http.controller.CreateUserController;
import http.controller.UserListController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static Map<String, Controller> controllers = new HashMap<>();
    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/users", new UserListController());
    }

    public static Controller getController(String requestUrl) {
        return controllers.get(requestUrl);
    }
}
