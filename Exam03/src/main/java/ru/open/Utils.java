package ru.open;

import java.lang.reflect.Proxy;


public class Utils {

    public static <T> T cache (T objIn) {
        return(T) Proxy.newProxyInstance(
            objIn.getClass().getClassLoader(),
            objIn.getClass().getInterfaces(),
            new CachingHandler(objIn)
            );
    }
}

