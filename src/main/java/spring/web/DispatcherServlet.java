package spring.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.mvc.handler.excution.HandlerAdapter;
import spring.mvc.handler.excution.HandlerExecutionHandlerAdapter;
import spring.mvc.handler.mapping.AnnotationHandlerMapping;
import spring.mvc.handler.mapping.HandlerMapping;
import spring.mvc.view.ModelAndView;
import spring.mvc.view.View;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMapping handlerMapping;
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    @Override
    public void init() {
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping("client.controller");
        ahm.initialize();
        this.handlerMapping = ahm;
        handlerAdapters.add(new HandlerExecutionHandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        String requestUri = req.getRequestURI();
        log.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Object handler = handlerMapping.getHandler(req);
        if (handler == null) {
            throw new IllegalArgumentException("존재하지 않는 URL입니다.");
        }

        try {
            ModelAndView mav = execute(handler, req, resp);
            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    private ModelAndView execute(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter.handle(req, resp, handler);
            }
        }
        return null;
    }
}