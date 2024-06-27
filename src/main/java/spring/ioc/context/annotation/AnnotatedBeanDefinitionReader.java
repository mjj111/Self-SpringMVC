package spring.ioc.context.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.ioc.annotation.Bean;
import spring.ioc.bean.support.BeanDefinitionReader;
import spring.ioc.bean.support.BeanDefinitionRegistry;
import spring.ioc.bean.support.BeanFactoryUtils;
import spring.ioc.bean.support.DefaultBeanDefinition;

import java.lang.reflect.Method;
import java.util.Set;

public class AnnotatedBeanDefinitionReader implements BeanDefinitionReader {
    private static final Logger log = LoggerFactory.getLogger(AnnotatedBeanDefinitionReader.class);

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void loadBeanDefinitions(Class<?>... annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            registerBean(annotatedClass);
        }
    }

    private void registerBean(Class<?> annotatedClass) {
        beanDefinitionRegistry.registerBeanDefinition(annotatedClass, new DefaultBeanDefinition(annotatedClass));
        Set<Method> beanMethods = BeanFactoryUtils.getBeanMethods(annotatedClass, Bean.class);
        for (Method beanMethod : beanMethods) {
            log.debug("@Bean method : {}", beanMethod);
            AnnotatedBeanDefinition abd = new AnnotatedBeanDefinition(beanMethod.getReturnType(), beanMethod);
            beanDefinitionRegistry.registerBeanDefinition(beanMethod.getReturnType(), abd);
        }
    }
}