package ru.open;

import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    public void clientNameActual() {
        try {
            Account acount = new Account("XXX");
        }
        catch (IllegalArgumentException err) {
            throw new RuntimeException("Test Balance error");
        }
    }

    @Test
    public void clientNameNotActual() {
        boolean error = false;

        try {
            Account acount = new Account("");
        } catch (IllegalArgumentException err) {
            error = true;
        }

        {
            try {
                Account acount = new Account(null);
            } catch (IllegalArgumentException err) {
                error = true;
            }
        }

        if (! error) throw new RuntimeException("Test clientName error");
    }



}
