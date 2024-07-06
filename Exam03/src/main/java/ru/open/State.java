package ru.open;


import lombok.EqualsAndHashCode;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EqualsAndHashCode
public class State {
    private final Map<Method, List<Object>> values = new HashMap<>();

    public State(){}

    public State(State oldState, Method method, Object[] args) {
        values.putAll(oldState.values);
        values.put(method, Arrays.asList(args));
    }

}