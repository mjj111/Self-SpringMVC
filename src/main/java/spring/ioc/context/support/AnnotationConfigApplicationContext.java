package spring.ioc.context.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.ioc.annotation.ComponentScan;
import spring.ioc.bean.support.BeanDefinitionReader;
import spring.ioc.bean.support.DefaultBeanFactory;
import spring.ioc.context.annotation.AnnotatedBeanDefinitionReader;
import spring.ioc.context.annotation.ClasspathBeanDefinitionScanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AnnotationConfigApplicationContext implements ApplicationContext {
    private static final Logger log = LoggerFactory.getLogger(AnnotationConfigApplicationContext.class);

    private DefaultBeanFactory beanFactory;

    public AnnotationConfigApplicationContext(Class<?>... clazzs) {
        Object[] basePackages = findBasePackages(clazzs);
        beanFactory = new DefaultBeanFactory();
        BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(clazzs);

        if (basePackages.length > 0) {
            ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
            scanner.doScan(basePackages);
        }
        beanFactory.preInstantiateSingletons();
    }

    private Object[] findBasePackages(Class<?>[] annotatedClasses) {
        List<Object> basePackages = new ArrayList<>();

        for (Class<?> annotatedClass : annotatedClasses) {

            ComponentScan componentScan = annotatedClass.getAnnotation(ComponentScan.class);
            if (componentScan == null) {
                continue;
            }

            for (String basePackage : componentScan.value()) {
                log.info("컴포넌트 스캔 범위, basePackage : {}", basePackage);
            }
            basePackages.addAll(Arrays.asList(componentScan.value()));
        }
        return basePackages.toArray();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    @Override
    public Set<Class<?>> getBeanClasses() {
        return beanFactory.getBeanClasses();
    }
}