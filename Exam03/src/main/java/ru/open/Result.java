package ru.open;

public class Result {
    long expireTime;
    Object value;

    public Result (long expireTime, Object value) {
        this.expireTime = expireTime;
        this.value = value;
    }
}
