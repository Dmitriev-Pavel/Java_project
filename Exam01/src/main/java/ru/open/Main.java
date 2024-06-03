package ru.open;

import static ru.open.Currency.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("___ 1. Заполнение данных");

        Account account = new Account("AAAAA");
        System.out.println(account);

        account.setClientName("BBBBB");
        System.out.println(account);

        account.setCurrData(RUB, 100);
        System.out.println(account);

        account.setCurrData(EUR, 222);
        System.out.println(account);

        account.setCurrData(EUR, 999);
        System.out.println(account);

        System.out.println("___ Часть 2. Отмена");

        account.undo();
        System.out.println(account);

        account.undo();
        System.out.println(account);

        //account.undo();
        //System.out.println(account);

        System.out.println("___ Часть 3. Сохранение");

        account.setClientName("CCCCC");
        account.setCurrData(RUB, 111);
        System.out.println(account);

        Loadable savePoint = account.save();

        account.setClientName("DDDDD");
        account.setCurrData(RUB, 222);

        System.out.println(account);

        savePoint.load();
        System.out.println(account);
    }


}
