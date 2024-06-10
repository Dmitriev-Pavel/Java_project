package ru.open;


import ru.open.annotation.Cache;
import ru.open.annotation.Mutator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Utils {

    // Обобщенный утилитный (static) метод.
    // Работает только с переданной в objIn интерфейсной ссылкой. С классовой ссылкой упадет
    public static <T> T cache (T objIn) {
        return(T) Proxy.newProxyInstance(
            objIn.getClass().getClassLoader(),
            objIn.getClass().getInterfaces(),
            new DynamicProxy(objIn)
            );
    }
}


// Динамический прокси
class DynamicProxy implements InvocationHandler {
    //Class       cls;
    //Object      obj
    //Method      mtd;
    //Field       fld;
    //Constructor ctr;
    //Annotation  ant;
    Object obj;
    Object cacheData;
    boolean isChanged = true;

    public DynamicProxy(Object obj) {
        this.obj = obj;
    }


    // Перехват любого метода
    @Override
    public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {
        method = obj.getClass().getMethod(method.getName(), method.getParameterTypes()); // Переопределим метод интерфейса на метод объекта

        if (method.isAnnotationPresent(Cache.class)) {
            if (isChanged) {
                cacheData = method.invoke(obj, args);
                isChanged = false;
            } else {
                return cacheData;
            }
        }

        if (method.isAnnotationPresent(Mutator.class)) {
            isChanged = true;
            return method.invoke(obj, args); // Возвращает вызов
        }

        return cacheData;
    }
}


