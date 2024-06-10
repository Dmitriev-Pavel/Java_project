package ru.open;

import org.junit.jupiter.api.*;

public class TestCacheHandler {

    @Test
    @DisplayName("Первичное кеширование единожды")
    public void  test0l() {
        TestFraction fraction = new TestFraction(1, 2);
        Fractionable fractionProxy = Utils.cache(fraction);

        fractionProxy.doubleValue();
        fractionProxy.doubleValue();

        Assertions.assertEquals(fraction.count, 1);
    }

    @Test
    @DisplayName("Два кеширования")
    public void test02() {
        TestFraction fraction = new TestFraction(1, 2);
        Fractionable fractionProxy = Utils.cache(fraction);

        fractionProxy.doubleValue();
        fractionProxy.setNum(4);
        fractionProxy.doubleValue();
        fractionProxy.doubleValue();

        System.out.println("count - " + fraction.count);
        Assertions.assertEquals(fraction.count, 2);
    }

    @Test
    @DisplayName("Результат")
    public void test03() {
        TestFraction fraction = new TestFraction(1, 2);
        Fractionable fractionProxy = Utils.cache(fraction);

        double d = fractionProxy.doubleValue();
        Assertions.assertEquals(0.5, d);
    }
}
