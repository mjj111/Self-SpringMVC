package spring.mvc.web.handler.mapping;

import jakarta.servlet.http.HttpServletRequest;
import spring.mvc.web.handler.excution.Handler;

public interface HandlerMapping {
    Handler getHandler(HttpServletRequest request);

    boolean supports(HttpServletRequest request);

    void initialize();
}
