package ru.open;

import java.util.ArrayDeque;
import java.util.ArrayList;


public class Account {
    private String clientName;
    private ArrayList<CurrData> currencyList = new ArrayList<>();
    private ArrayDeque<Performed> arrActions = new ArrayDeque<>();

    private Account() {}

    public Account (String clientName) {
        this.setClientName(clientName);
        this.currencyList = new ArrayList<>();
    }

    public String getClientName() {
        return clientName;
    }


    public void setClientName(String clientName) {
        if (clientName == null || clientName == "") throw new IllegalArgumentException("Не задано наименование клиента");

        String cancName = this.clientName;

        arrActions.push(()->{this.clientName = cancName;});
        this.clientName = clientName;
    }


    public void setCurrData (Currency currency, int balance){
        CurrData currData = getCurrData(currency);

        if (currData == null) {
            CurrData cur = new CurrData(currency, balance);
            currencyList.add(cur);
            arrActions.push(()->{currencyList.remove(cur);});
        } else {
            int oldBalance = currData.getBalance();
            arrActions.push(()->{currData.setBalance(oldBalance);});
            currData.setBalance(balance);
        }
    }


    public CurrData getCurrData (Currency currency){
        for (int i = 0; i < currencyList.stream().count(); i++) {
            if (currencyList.get(i).getCurrency().name() == currency.name()) {
                return currencyList.get(i);
            }
        }

        return null;
    }


    private class SavePoint implements Loadable {
        private String clientName;
        private ArrayList<CurrData> currencyList = new ArrayList<CurrData>();

        public SavePoint (){
            clientName = Account.this.clientName;

            currencyList.clear();
            for (int i = 0; i < Account.this.currencyList.stream().count(); i++) {
                CurrData cur = new CurrData(Account.this.currencyList.get(i).getCurrency(), Account.this.currencyList.get(i).getBalance());
                currencyList.add(cur);
            }
        }

        @Override
        public void load() {
            Account.this.clientName = clientName;

            Account.this.currencyList.clear();
            for (int i = 0; i < currencyList.stream().count(); i++) {
                CurrData cur = new CurrData(currencyList.get(i).getCurrency(), currencyList.get(i).getBalance());
                Account.this.currencyList.add(cur);
            }
        }
    }


    public Loadable save(){
        return new SavePoint();
    }


    public Account undo() throws Exception {
        if (arrActions.isEmpty()) throw new Exception("Отсутствуют сохраненные действия");

        arrActions.pop().perform();
        return this;
    }


    @Override
    public String toString() {
        return "clientName - " + getClientName() + "; " + currencyList.toString();
    }
}

