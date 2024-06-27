package spring.ioc.bean;

public interface ConfigurableListableBeanFactory extends BeanFactory {
    void preInstantiateSingletons();
}