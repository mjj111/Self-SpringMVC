package spring.mvc;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcherServlet", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
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
            String result = controller.execute(request, response);
            response.getWriter().println(result);
            log.debug("Response Result : {}", result);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
