package spring.ioc.context.annotation;

import org.reflections.Reflections;
import spring.ioc.annotation.Component;
import spring.mvc.annotation.Controller;
import spring.ioc.annotation.Repository;
import spring.ioc.annotation.Service;
import spring.ioc.bean.support.BeanDefinitionRegistry;
import spring.ioc.bean.support.DefaultBeanDefinition;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class ClasspathBeanDefinitionScanner {
    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public ClasspathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void doScan(Object... basePackages) {
        Reflections reflections = new Reflections(basePackages);
        Set<Class<?>> beanClasses = getTypesAnnotatedWith(reflections, Controller.class, Service.class,
                Repository.class, Component.class);
        for (Class<?> clazz : beanClasses) {
            beanDefinitionRegistry.registerBeanDefinition(clazz, new DefaultBeanDefinition(clazz));
        }
    }

    @SafeVarargs
    private Set<Class<?>> getTypesAnnotatedWith(Reflections reflections, Class<? extends Annotation>... annotations) {
        Set<Class<?>> preInstantiatedBeans = new HashSet<>();
        for (Class<? extends Annotation> annotation : annotations) {
            preInstantiatedBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return preInstantiatedBeans;
    }
}