package ru.open;


public class Main {
    public static void main(String[] args) {

        Fractionable fraction = new Fraction(5, 2);
        fraction = Utils.cache(fraction);

        System.out.println(fraction.doubleValue());
        System.out.println(fraction.doubleValue());
        System.out.println(fraction.doubleValue());

        fraction.setNum(10);
        System.out.println(fraction.doubleValue());
        System.out.println(fraction.doubleValue());

    }
}