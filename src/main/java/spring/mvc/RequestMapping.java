package spring.mvc;

import spring.contorller.CreatUserController;
import spring.contorller.UserListController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/users", new UserListController());
        mappings.put("/users/create", new CreatUserController());
        log.info("RequestMapping Init Success");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }
}
