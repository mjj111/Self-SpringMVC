package spring.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.mvc.controller.Controller;
import spring.mvc.view.ModelAndView;
import spring.mvc.view.View;

@WebServlet(name = "dispatcherServlet", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private RequestMapping requestMapping;

    @Override
    public void init() {
        requestMapping = new RequestMapping();
        requestMapping.initMapping();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        String requestUri = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestUri);

        Controller controller = requestMapping.findController(requestUri);
        try {
            ModelAndView result = controller.execute(request, response);
            View view = result.getView();
            view.render(result.getModel(), request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
