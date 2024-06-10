package ru.open;

import ru.open.annotation.Cache;
import ru.open.annotation.Mutator;


public class Fraction implements Fractionable {
    private int num;
    private int denum;
    boolean isChanged = true;

    public Fraction(int num, int denum) {
        this.num = num;
        this.denum = denum;
    }

    @Mutator
    public void setNum(int num) {
        this.num = num;
    }

    @Mutator
    public void setDenum(int denum) {
        this.denum = denum;
    }

    @Override
    @Cache
    public double doubleValue() {
        System.out.print("Расчитанное значение - ");
        return (double) num / denum;
    }

}
