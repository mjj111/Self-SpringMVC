package spring.ioc.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BeanUtils {
    private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

    public static <T> T instantiate(Class<T> clazz) {
        if (clazz.isInterface()) {
            throw new RuntimeException("인터페이스입니다.");
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException ex) {
            // 기본 생성자가 없는 경우, 다른 생성자를 사용
            return instantiateWithSuitableConstructor(clazz);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException("클래스 인스턴스화 오류: " + clazz.getName(), ex);
        }
    }

    private static <T> T instantiateWithSuitableConstructor(Class<T> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        Constructor<?> constructor = constructors[0]; // 첫 번째 생성자를 사용

        // 생성자의 파라미터 타입에 맞는 기본 인스턴스를 생성하여 주입
        Object[] params = Arrays.stream(constructor.getParameterTypes())
                .map(BeanUtils::getDefaultInstance)
                .collect(Collectors.toList())
                .toArray();

        try {
            constructor.setAccessible(true);
            return (T) constructor.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException("클래스 인스턴스화 오류: " + clazz.getName(), ex);
        }
    }

    private static Object getDefaultInstance(Class<?> paramType) {
        if (paramType.isPrimitive()) {
            if (paramType == boolean.class) return false;
            if (paramType == char.class) return '\0';
            if (paramType == byte.class || paramType == short.class || paramType == int.class || paramType == long.class)
                return 0;
            if (paramType == float.class || paramType == double.class) return 0.0;
        }
        try {
            return instantiate(paramType);
        } catch (RuntimeException e) {
            throw new RuntimeException("파라미터 타입 인스턴스화 오류: " + paramType.getName(), e);
        }
    }


    public static Object instantiateClass(final Constructor<?> constructor, final Object[] args) {
        try {
            log.debug("Instantiating class using constructor: {}", constructor.getName());
            return constructor.newInstance(args);
        } catch (InstantiationException e) {
            throw new RuntimeException("클래스를 인스턴스화 할 수 없습니다: " + constructor.getDeclaringClass().getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("생성자에 접근할 수 없습니다: " + constructor.getDeclaringClass().getName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("생성자 호출 중 예외가 발생했습니다: " + constructor.getDeclaringClass().getName(), e);
        }
    }
}
