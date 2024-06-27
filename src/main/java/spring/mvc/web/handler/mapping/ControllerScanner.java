package spring.mvc.web.handler.mapping;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.ioc.context.support.ApplicationContext;
import spring.mvc.annotation.Controller;
import spring.mvc.annotation.RequestMapping;
import spring.mvc.web.handler.excution.AnnotationHandler;
import spring.mvc.web.handler.excution.Handler;
import spring.mvc.web.handler.excution.HandlerExecution;

import java.lang.reflect.Method;
import java.util.*;

public class ControllerScanner {
    private final Reflections reflections;
    private final Logger log = LoggerFactory.getLogger(ControllerScanner.class);

    public ControllerScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public static ControllerScanner withScanRange(final Object[] basePackage) {
        return new ControllerScanner(basePackage);
    }

    public void putHandlersToHandlerMap(Map<HandlerKey, Handler> handlerExecutions, ApplicationContext applicationContext) {
        for (Class<?> controllerType : reflections.getTypesAnnotatedWith(Controller.class)) {
            Object controller = getControllerInstance(controllerType, applicationContext);
            List<Method> controllerMethods = getControllerMethods(controllerType);
            controllerMethods.forEach(method -> addHandlerExecutions(controller, method, handlerExecutions));
        }
    }

    private Object getControllerInstance(final Class<?> controllerType, final ApplicationContext applicationContext){
        try {
            return applicationContext.getBean(controllerType);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private List<Method> getControllerMethods(final Class<?> controllerType) {
        return Arrays.stream(controllerType.getMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .toList();
    }

    private void addHandlerExecutions(final Object controller, final Method method, Map<HandlerKey, Handler> handlerExecutions) {
        RequestMapping request = method.getAnnotation(RequestMapping.class);
        HandlerExecution handlerExecution = new HandlerExecution(controller, method);
        AnnotationHandler handler = new AnnotationHandler(handlerExecution);

        Arrays.stream(request.method())
                .map(httpMethod -> new HandlerKey(request.value(), httpMethod))
                .forEach(handlerKey -> handlerExecutions.put(handlerKey, handler));
    }
}
