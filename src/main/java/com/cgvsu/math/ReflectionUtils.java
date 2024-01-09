package com.cgvsu.math;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unchecked")
public final class ReflectionUtils {
    public static <V, E> V getResultOfBiFunction(V object1, E object2, BiFunction<V, E> biFunction) {
        V objectToReturn = createNewObject(object1);
        biFunction.apply(objectToReturn, object2);
        return objectToReturn;
    }

    public static <V> V getResultOfFunction(V object, Function<V> function) {
        V objectToReturn = createNewObject(object);
        function.apply(objectToReturn);
        return objectToReturn;
    }

    public static <V> Constructor<?> getConstructorOfObject(V object, Class<?> ... parameterTypes) {
        try {
            return object.getClass().getDeclaredConstructor(parameterTypes);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <V> V createNewObject(V object) {
        try {
            return (V) getConstructorOfObject(object, object.getClass().getSuperclass()).newInstance(object);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface BiFunction<V, E> {
        void apply(V object1, E object2);
    }

    @FunctionalInterface
    public interface Function<V> {
        void apply(V object);
    }
}
