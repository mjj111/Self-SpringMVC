package spring.ioc.bean.support;

import static org.reflections.ReflectionUtils.getAllConstructors;
import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withAnnotation;
import static org.reflections.ReflectionUtils.withReturnType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.ioc.annotation.Inject;


public class BeanFactoryUtils {
    private static final Logger log = LoggerFactory.getLogger(BeanFactoryUtils.class);

    public static Set<Method> getInjectedMethods(Class<?> clazz) {
        return getAllMethods(clazz, withAnnotation(Inject.class), withReturnType(void.class));
    }

    public static Set<Method> getBeanMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        return getAllMethods(clazz, withAnnotation(annotation));
    }

    public static Set<Field> getInjectedFields(Class<?> clazz) {
        return getAllFields(clazz, withAnnotation(Inject.class));
    }

    public static Set<Constructor> getInjectedConstructors(Class<?> clazz) {
        return getAllConstructors(clazz, withAnnotation(Inject.class));
    }

    public static Optional<Object> invokeMethod(Method method, Object bean, Object[] args) {
        try {
            return Optional.ofNullable(method.invoke(bean, args));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
        Set<Constructor> injectedConstructors = getAllConstructors(clazz, withAnnotation(Inject.class));
        if (injectedConstructors.isEmpty()) {
            return null;
        }
        return injectedConstructors.iterator().next();
    }


    public static Optional<Class<?>> findConcreteClass(Class<?> injectedClazz, Set<Class<?>> preInstanticateBeans) {
        if (!injectedClazz.isInterface()) {
            return Optional.of(injectedClazz);
        }

        for (Class<?> clazz : preInstanticateBeans) {
            Set<Class<?>> interfaces = new HashSet<>(Arrays.asList(clazz.getInterfaces()));
            if (interfaces.contains(injectedClazz)) {
                return Optional.of(clazz);
            }
        }

        return Optional.empty();
    }
}