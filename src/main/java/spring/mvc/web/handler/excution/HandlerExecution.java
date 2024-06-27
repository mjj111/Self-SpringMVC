package spring.mvc.web.handler.excution;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.mvc.view.ModelAndView;
import java.lang.reflect.Method;


public class HandlerExecution {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);

    private final Object invoker;
    private final Method method;

    public HandlerExecution(final Object invoker, final Method method) {
        this.invoker = invoker;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            return (ModelAndView) method.invoke(invoker, request, response);
        } catch (Exception e) {
            log.error("{} 메서드 호출 실패 : {}", method, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}