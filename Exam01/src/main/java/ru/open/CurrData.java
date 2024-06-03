package ru.open;

public class CurrData {

    private Currency currency;
    private int balance;

    public int getBalance(){
        return balance;
    }

    public Currency getCurrency (){
        return currency;
    }

    public void setBalance (int value){
        if (value < 0) throw new IllegalArgumentException("Отрицательное значение недопустимо.");
        this.balance = value;
    }

    public CurrData (Currency currency, int balance){
        this.currency = currency;
        setBalance(balance);
    }

    @Override
    public String toString() {
        return currency.name() + " = " + balance;
    }

}
