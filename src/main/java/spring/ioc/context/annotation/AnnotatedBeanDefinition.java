package spring.ioc.context.annotation;

import spring.ioc.bean.support.DefaultBeanDefinition;

import java.lang.reflect.Method;

public class AnnotatedBeanDefinition extends DefaultBeanDefinition {
    private Method method;

    public AnnotatedBeanDefinition(Class<?> clazz, Method method) {
        super(clazz);
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }
}