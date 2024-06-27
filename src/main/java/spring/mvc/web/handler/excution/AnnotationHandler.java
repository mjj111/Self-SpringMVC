package spring.mvc.web.handler.excution;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import spring.mvc.view.ModelAndView;
import spring.mvc.view.View;
import java.util.Map;

public class AnnotationHandler implements Handler {

    private final HandlerExecution handlerExecution;

    public AnnotationHandler(final HandlerExecution handlerExecution) {
        this.handlerExecution = handlerExecution;
    }

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        try {
            ModelAndView modelAndView = handlerExecution.handle(request, response);

            View view = modelAndView.getView();
            Map<String, Object> model = modelAndView.getModel();

            view.render(model, request, response);
        } catch (final Exception e) {
            throw new ServletException(e.getMessage());
        }
    }
}
