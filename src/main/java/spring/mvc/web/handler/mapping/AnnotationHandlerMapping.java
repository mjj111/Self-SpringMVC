package spring.mvc.web.handler.mapping;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.ioc.context.support.ApplicationContext;
import spring.mvc.web.handler.excution.Handler;

import java.util.*;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final ApplicationContext applicationContext;
    private final Object[] basePackage;
    private final Map<HandlerKey, Handler> handlerMap;

    public AnnotationHandlerMapping(ApplicationContext applicationContext, Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerMap = new HashMap<>();
        this.applicationContext = applicationContext;
    }

    public void initialize() {
        ControllerScanner sc = ControllerScanner.withScanRange(basePackage);
        sc.putHandlersToHandlerMap(handlerMap, applicationContext);
        for(HandlerKey key: handlerMap.keySet()) {
            logger.debug("Mapped : {}",key.toString());
        }
    }

    @Override
    public boolean supports(final HttpServletRequest request) {
        HandlerKey handlerKey = HandlerKey.from(request);
        return handlerMap.containsKey(handlerKey);
    }

    @Override
    public Handler getHandler(final HttpServletRequest request) {
        HandlerKey handlerKey = HandlerKey.from(request);
        return handlerMap.get(handlerKey);
    }
}