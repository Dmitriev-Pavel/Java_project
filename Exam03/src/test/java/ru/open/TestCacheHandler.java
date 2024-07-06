package ru.open;

import org.junit.jupiter.api.*;

public class TestCacheHandler {

    @Test
    @DisplayName("Первый вызов обычный, второй из кэша")
    public void  test0l() {
        TestFraction fraction = new TestFraction(2, 3);
        Fractionable num = Utils.cache(fraction);

        num.doubleValue();
        num.doubleValue();
        Assertions.assertEquals(fraction.count, 1);
    }

    @Test
    @DisplayName("Объект вернулся к предыдущему состоянию")
    public void test02() {
        TestFraction fraction = new TestFraction(2, 3);
        Fractionable num = Utils.cache(fraction);

        num.setNum(2);
        num.doubleValue();
        num.doubleValue();
        Assertions.assertEquals(fraction.count, 1);
        fraction.count = 0;

        num.setNum(5);
        num.doubleValue();
        num.doubleValue();
        System.out.println("setNum(5) count = " + fraction.count);
        Assertions.assertEquals(fraction.count, 1);
        fraction.count = 0;

        num.setNum(2);
        num.doubleValue();
        num.doubleValue();
        System.out.println("setNum(2) count = " + fraction.count);
        Assertions.assertEquals(fraction.count, 0);
    }

    @Test
    @DisplayName("Thread.sleep")
    public void test03() throws InterruptedException {
        TestFraction fraction = new TestFraction(2, 3);
        Fractionable num = Utils.cache(fraction);

        num.setNum(2);
        num.doubleValue();
        num.doubleValue();
        Assertions.assertEquals(fraction.count, 1);
        fraction.count = 0;

        num.setNum(5);
        num.doubleValue();
        num.doubleValue();
        System.out.println("setNum(5) count = " + fraction.count);
        Assertions.assertEquals(fraction.count, 1);
        fraction.count = 0;

        num.setNum(2);
        num.doubleValue();
        num.doubleValue();
        Assertions.assertEquals(fraction.count, 0);
        fraction.count = 0;

        Thread.sleep(1500);

        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит
        Assertions.assertEquals(fraction.count, 1);
    }


    @Test
    @DisplayName("doubleValue()")
    public void test04() throws InterruptedException {
        TestFraction fraction = new TestFraction(2, 3);
        Fractionable num = Utils.cache(fraction);

        double d = num.doubleValue();
        Assertions.assertEquals((double) 2 / 3, d);
    }
}
