package ru.open;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        Fraction fraction = new Fraction(2, 3);
        Fractionable num = Utils.cache(fraction);

        // В первой паре значений: первый вызов обычный, второй дает значение из кэша
        num.setNum(2);
        System.out.println("1)");
        System.out.println(num.doubleValue());// sout сработал
        System.out.println(num.doubleValue());// sout молчит

        // Вторая пара значений вызывается после смены состояния объекта, и следовательно первый вызов обычный, второй кэшированный.
        System.out.println("2) setNum(5)");
        num.setNum(5);
        System.out.println(num.doubleValue());// sout сработал
        System.out.println(num.doubleValue());// sout молчит

        // Третья пара вызывается после того, как объект вернулся к состоянию, в котором он был ранее, поэтому возвращается в обоих случаях кэшированная версия
        System.out.println("3) setNum(2)");
        num.setNum(2);
        System.out.println(num.doubleValue());// sout молчит
        System.out.println(num.doubleValue());// sout молчит

        // Последняя пара вызывается спустя 1500 миллисекунд после последнего обращения к кэшу вообще, следовательно срок жизни объектов в кэше закончился, и необходимо пересчитывать кэшированное значение заново.
        // Таким образом первый раз вызывается обычный метод, второй раз возвращается обновленный кэш.
        System.out.println("4) sleep(1500)");
        Thread.sleep(1500);

        System.out.println(num.doubleValue());// sout сработал
        System.out.println(num.doubleValue());// sout молчит

    }
}