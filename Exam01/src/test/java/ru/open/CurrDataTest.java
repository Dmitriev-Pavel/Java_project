package ru.open;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import static ru.open.Currency.*;


public class CurrDataTest {

    @Test
    public void positiveBalance() {
        try {
            CurrData cur = new CurrData(RUB, 1);
        }
        catch (IllegalArgumentException err) {
            throw new RuntimeException("Test Balance error");
        }
    }

    @Test
    public void negativeBalance() {
        try {
            CurrData cur = new CurrData(RUB, -1);
        }
        catch (IllegalArgumentException err) {
            return;
        }
        throw new RuntimeException("Test Balance error");
    }
}
