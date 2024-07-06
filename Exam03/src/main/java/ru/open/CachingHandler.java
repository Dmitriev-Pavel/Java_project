package ru.open;

import ru.open.annotation.Cache;
import ru.open.annotation.Mutator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.Object;


public class CachingHandler <T> implements InvocationHandler {

    private final T currentObject;
    private final Map<State, Map<Method, Result>> stateMap = new HashMap<>();

    private State curState = new State();  // Текущее состояние всей мапы состояний
    private Map<Method, Result> curMethodResult = new ConcurrentHashMap<>();

    private long timeCleaner = System.currentTimeMillis();

    public CachingHandler(T currentObject) {
        this.currentObject = currentObject;
        stateMap.put(curState, curMethodResult);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object objectResult;
        Method currentMethod = currentObject.getClass().getMethod(method.getName(), method.getParameterTypes());

        // @Mutator (setNum)
        if (currentMethod.isAnnotationPresent(Mutator.class)) {
            curState = new State(curState, currentMethod, args);

            if (stateMap.containsKey(curState)) {
                curMethodResult = stateMap.get(curState);
            } else {
                curMethodResult = new ConcurrentHashMap();
                stateMap.put(curState, curMethodResult);
            }

            new CacheCleaner().start();
        }

        // @Cache (doubleValue)
        if (currentMethod.isAnnotationPresent(Cache.class)) {
            long lifeTime = currentMethod.getAnnotation(Cache.class).value();
            Result result = curMethodResult.get(currentMethod);   // Получить кешированное значение, которое сейчас есть в мапе с результатами

            if (result != null) {
                long curTime = System.currentTimeMillis();

                if (lifeTime == 0) {
                    result.expireTime = 0L;
                } else {
                    if (result.expireTime > curTime) {
                        result.expireTime = curTime + lifeTime; // Обновим время
                    }
                }

                curMethodResult.put(currentMethod, result);

                if (lifeTime == 0 | result.expireTime >= curTime) { // Еще жив
                    return result.value;
                }
            }

            objectResult = method.invoke(currentObject, args);
            result = new Result(System.currentTimeMillis() + lifeTime, objectResult);

            if (lifeTime == 0) result.expireTime = 0L;

            curMethodResult.put(currentMethod, result);
            return objectResult;
        }

        return method.invoke(currentObject, args);
    }




    private class CacheCleaner extends Thread {
        @Override
        public void run(){
            for (Map<Method, Result> map : stateMap.values()) {
                for (Method met : map.keySet()) {
                    Result result = map.get(met);

                    if (result.expireTime == 0) {
                        continue;
                    }

                    if (result.expireTime < System.currentTimeMillis()) {
                        map.remove(met);
                    }
                }
            }
        }
    }
}
