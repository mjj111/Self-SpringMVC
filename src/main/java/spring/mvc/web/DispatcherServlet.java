package spring.mvc.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.ioc.context.support.ApplicationContext;
import spring.mvc.web.handler.excution.Handler;
import spring.mvc.web.handler.mapping.AnnotationHandlerMapping;
import spring.mvc.web.handler.mapping.HandlerMapping;

import java.io.IOException;


public class DispatcherServlet extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private final ApplicationContext applicationContext;
    private HandlerMapping handlerMapping;

    public DispatcherServlet(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void init() {
        this.handlerMapping = new AnnotationHandlerMapping(applicationContext, "client");
        handlerMapping.initialize();
    }

    @Override
    protected void service(final HttpServletRequest request,
                           final HttpServletResponse response) {
        log.info("Request : {} {}", request.getMethod(), request.getRequestURI());
        if(!handlerMapping.supports(request)) {
            handleNotSupported(request, response);
            return;
        }

        Handler handler = handlerMapping.getHandler(request);
        try {
            handler.handle(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
            handleNotSupported(request, response);
        }

    }

    private void handleNotSupported(final HttpServletRequest request,
                                    final HttpServletResponse response) {
        log.warn("지원하지 않는 요청 URI: {} ", request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        try {
            response.getWriter().write("해당 URI에 대한 접근은 지원하지 않는 요청입니다. : " + request.getRequestURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}