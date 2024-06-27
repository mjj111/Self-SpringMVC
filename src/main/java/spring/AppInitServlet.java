package spring;

import client.Application;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.ioc.context.support.ApplicationContext;
import spring.ioc.context.support.AnnotationConfigApplicationContext;
import spring.mvc.web.DispatcherServlet;

import java.util.Set;

public class AppInitServlet implements ServletContainerInitializer {

    private static final String DEFAULT_SERVLET_NAME = "DispatcherServlet";
    private static final Logger log = LoggerFactory.getLogger(AppInitServlet.class);

    @Override
    public void onStartup(final Set<Class<?>> c, final ServletContext servletContext) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);

        servletContext
                .addServlet(DEFAULT_SERVLET_NAME, dispatcherServlet)
                .addMapping("/");
        log.info("서블릿 등록 : {}",dispatcherServlet.getClass().getName());
    }
}