
package waypoint.model;

import java.util.*;
import java.math.BigInteger;

public final class Money implements Comparable<Money> {

    private final long paise;
    private final Currency currency;

    // Private constructor
    private Money(long paise, Currency currency) {
        this.paise = paise;
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
    }

    // Factory method using minor units (paise)
    public static Money ofMinor(long amount, Currency currency) {
        return new Money(amount, currency);
    }

    // Factory method using rupees
    public static Money rupees(long amount) {
        return new Money(Math.multiplyExact(amount, 100L),
                Currency.getInstance("INR"));
    }

    // Getters
    public long getPaise() {
        return paise;
    }

    public Currency getCurrency() {
        return currency;
    }

    // Check currency compatibility
    private void checkCurrency(Money other) {
        Objects.requireNonNull(other, "Money cannot be null");

        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
    }

    // Addition
    public Money plus(Money other) {
        checkCurrency(other);
        return new Money(Math.addExact(paise, other.paise), currency);
    }

    // Subtraction
    public Money minus(Money other) {
        checkCurrency(other);
        return new Money(Math.subtractExact(paise, other.paise), currency);
    }

    // Multiplication
    public Money times(int multiplier) {
        return new Money(Math.multiplyExact(paise, multiplier), currency);
    }

    // Check negative amount
    public boolean isNegative() {
        return paise < 0;
    }

    // Compare money values
    @Override
    public int compareTo(Money other) {
        checkCurrency(other);
        return Long.compare(paise, other.paise);
    }

    // Allocate money equally
    public List<Money> allocate(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("Number of shares must be positive");
        }

        List<Money> shares = new ArrayList<>();

        long quotient = paise / n;
        long remainder = paise % n;

        long extra = Math.abs(remainder);

        for (int i = 0; i < n; i++) {

            long amount = quotient;

            if (i < extra) {
                amount += (paise < 0) ? -1 : 1;
            }

            shares.add(new Money(amount, currency));
        }

        return Collections.unmodifiableList(shares);
    }

    // Equality
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Money)) {
            return false;
        }

        Money other = (Money) obj;

        return paise == other.paise &&
                currency.equals(other.currency);
    }

    // Hash code
    @Override
    public int hashCode() {
        return Objects.hash(paise, currency);
    }

    // String representation
    @Override
    public String toString() {

        BigInteger amount = BigInteger.valueOf(paise);
        boolean negative = amount.signum() < 0;

        amount = amount.abs();

        BigInteger[] parts = amount.divideAndRemainder(
                BigInteger.valueOf(100));

        String result = currency.getCurrencyCode() + " " +
                parts[0] + "." +
                String.format("%02d", parts[1].intValue());

        return negative ? "-" + result : result;
    }
}
class CurrencyMismatch extends RuntimeException {
    public CurrencyMismatch(String message) {
        super(message);
    }
}