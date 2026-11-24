package waypoint.model;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void testRupees() {
        Money money = Money.rupees(100);
        assertEquals(10000, money.getPaise());
    }

    @Test
    void testOfMinor() {
        Money money = Money.ofMinor(500, Currency.getInstance("INR"));
        assertEquals(500, money.getPaise());
    }

    @Test
    void testAddition() {
        Money a = Money.rupees(100);
        Money b = Money.rupees(50);

        assertEquals(Money.rupees(150), a.plus(b));
    }

    @Test
    void testSubtraction() {
        Money a = Money.rupees(100);
        Money b = Money.rupees(40);

        assertEquals(Money.rupees(60), a.minus(b));
    }

    @Test
    void testMultiplication() {
        Money money = Money.rupees(100);

        assertEquals(Money.rupees(300), money.times(3));
    }

    @Test
    void testNegativeMoney() {
        Money money = Money.ofMinor(-500, Currency.getInstance("INR"));

        assertTrue(money.isNegative());
    }

    @Test
    void testComparison() {
        Money a = Money.rupees(100);
        Money b = Money.rupees(200);

        assertTrue(a.compareTo(b) < 0);
    }

    @Test
    void testCurrencyMismatch() {
        Money a = Money.ofMinor(100, Currency.getInstance("INR"));
        Money b = Money.ofMinor(100, Currency.getInstance("USD"));

        assertThrows(IllegalArgumentException.class, () -> a.plus(b));
    }

    @Test
    void testAllocation() {
        Money money = Money.ofMinor(100, Currency.getInstance("INR"));

        List<Money> shares = money.allocate(3);

        assertEquals(3, shares.size());

        long total = shares.stream()
                .mapToLong(Money::getPaise)
                .sum();

        assertEquals(100, total);
    }

    @Test
    void testInvalidAllocation() {
        Money money = Money.rupees(100);

        assertThrows(IllegalArgumentException.class,
                () -> money.allocate(0));
    }

    @Test
    void testToString() {
        Money money = Money.rupees(100);

        assertEquals("INR 100.00", money.toString());
    }
}