package ru.open;

import ru.open.annotation.Cache;
import ru.open.annotation.Mutator;

public class TestFraction implements Fractionable {
    int num, denum;
    int count;

    public TestFraction(int num, int denum) {
        this.num = num;
        this.denum = denum;
    }

    @Override
    @Mutator
    public void setNum(int num) {
        this.num = num;
    }

    @Override
    @Mutator
    public void setDenum(int denum) {
        this.denum = denum;
    }

    @Override
    @Cache(1000)
    public double doubleValue() {
        count++;
        return (double) num / denum;
    }

    @Override
    public String toString(){
        return "TestFraction {num = " + num + ", denum = " + denum + ", count = " + count + "}";
    }

}
