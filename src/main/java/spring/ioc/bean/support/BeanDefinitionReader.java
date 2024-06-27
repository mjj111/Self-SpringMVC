package spring.ioc.bean.support;

public interface BeanDefinitionReader {
    void loadBeanDefinitions(Class<?>... annotatedClasses);
}
