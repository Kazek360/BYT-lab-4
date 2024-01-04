package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
    Currency SEK, DKK, NOK, EUR;
    Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

    @Before
    public void setUp() throws Exception {
        SEK = new Currency("SEK", 0.15);
        DKK = new Currency("DKK", 0.20);
        EUR = new Currency("EUR", 1.5);
        SEK100 = new Money(10000, SEK);
        EUR10 = new Money(1000, EUR);
        SEK200 = new Money(20000, SEK);
        EUR20 = new Money(2000, EUR);
        SEK0 = new Money(0, SEK);
        EUR0 = new Money(0, EUR);
        SEKn100 = new Money(-10000, SEK);
    }

    @Test
    public void testGetAmount() {
        // Sprawdza, czy metoda getAmount() zwraca poprawne ilości pieniędzy
        assertEquals(Integer.valueOf(10000), SEK100.getAmount());
        assertEquals(Integer.valueOf(1000), EUR10.getAmount());
        assertEquals(Integer.valueOf(20000), SEK200.getAmount());
        assertEquals(Integer.valueOf(2000), EUR20.getAmount());
        assertEquals(Integer.valueOf(0), SEK0.getAmount());
        assertEquals(Integer.valueOf(0), EUR0.getAmount());
        assertEquals(Integer.valueOf(-10000), SEKn100.getAmount());
    }

    @Test
    public void testGetCurrency() {
        // Sprawdza, czy metoda getCurrency() zwraca poprawną walutę
        assertEquals(SEK, SEK100.getCurrency());
        assertEquals(EUR, EUR10.getCurrency());
        assertEquals(SEK, SEK200.getCurrency());
        assertEquals(EUR, EUR20.getCurrency());
        assertEquals(SEK, SEK0.getCurrency());
        assertEquals(EUR, EUR0.getCurrency());
        assertEquals(SEK, SEKn100.getCurrency());
    }

    @Test
    public void testToString() {
        // Sprawdza, czy metoda toString() zwraca poprawny łańcuch znakowy
        assertEquals("100,00 SEK", SEK100.toString());
        assertEquals("10,00 EUR", EUR10.toString());
        assertEquals("200,00 SEK", SEK200.toString());
        assertEquals("20,00 EUR", EUR20.toString());
        assertEquals("0,00 SEK", SEK0.toString());
        assertEquals("0,00 EUR", EUR0.toString());
        assertEquals("-100,00 SEK", SEKn100.toString());
    }


    @Test
    public void testGlobalValue() {
        // Sprawdza, czy metoda universalValue() zwraca poprawną globalną wartość
        assertEquals(Integer.valueOf(1500), SEK100.universalValue());
        assertEquals(Integer.valueOf(1500), EUR10.universalValue());
        assertEquals(Integer.valueOf(3000), SEK200.universalValue());
        assertEquals(Integer.valueOf(3000), EUR20.universalValue());
        assertEquals(Integer.valueOf(0), SEK0.universalValue());
        assertEquals(Integer.valueOf(0), EUR0.universalValue());
        assertEquals(Integer.valueOf(-1500), SEKn100.universalValue());
    }

    @Test
    public void testEqualsMoney() {
        // Sprawdza, czy metoda equals() porównuje pieniądze poprawnie
        assertTrue(SEK100.equals(new Money(10000, SEK)));
        assertTrue(EUR10.equals(new Money(1000, EUR)));
        assertTrue(SEK200.equals(new Money(20000, SEK)));
        assertTrue(EUR20.equals(new Money(2000, EUR)));
        assertTrue(SEK0.equals(new Money(0, SEK)));
        assertTrue(EUR0.equals(new Money(0, EUR)));
        assertTrue(SEKn100.equals(new Money(-10000, SEK)));

        assertFalse(SEK100.equals(EUR10));
        assertFalse(SEK100.equals(SEK200));
        assertFalse(EUR20.equals(EUR0));
    }

    @Test
    public void testAdd() {
        // Sprawdza, czy metoda add() dodaje pieniądze poprawnie
        Money result1 = SEK100.add(new Money(1000, EUR));
        assertEquals(Integer.valueOf(11500), result1.getAmount());

        Money result2 = EUR10.add(new Money(20000, SEK));
        assertEquals(Integer.valueOf(4000), result2.getAmount());

        Money result3 = SEK200.add(new Money(2000, EUR));
        assertEquals(Integer.valueOf(23000), result3.getAmount());

        Money result4 = EUR20.add(new Money(0, SEK));
        assertEquals(Integer.valueOf(2000), result4.getAmount());
    }

    @Test
    public void testSub() {
        // Sprawdza, czy metoda sub() odejmuje pieniądze poprawnie
        Money result1 = SEK100.sub(new Money(1000, EUR));
        assertEquals(Integer.valueOf(8500), result1.getAmount());

        Money result2 = EUR10.sub(new Money(20000, SEK));
        assertEquals(Integer.valueOf(-2000), result2.getAmount());

        Money result3 = SEK200.sub(new Money(2000, EUR));
        assertEquals(Integer.valueOf(17000), result3.getAmount());

        Money result4 = EUR20.sub(new Money(0, SEK));
        assertEquals(Integer.valueOf(2000), result4.getAmount());
    }


    @Test
    public void testIsZero() {
        // Sprawdza, czy metoda isZero() zwraca poprawną wartość
        assertTrue(SEK0.isZero());
        assertFalse(EUR10.isZero());
        assertFalse(SEK100.isZero());
        assertFalse(EUR20.isZero());
        assertTrue(new Money(0, SEK).isZero());
        assertTrue(new Money(0, EUR).isZero());
        assertFalse(SEKn100.isZero());
    }


    @Test
    public void testNegate() {
        // Sprawdza, czy metoda negate() odwraca wartość pieniędzy poprawnie
        Money result1 = SEK100.negate();
        assertEquals(Integer.valueOf(-10000), result1.getAmount());

        Money result2 = EUR10.negate();
        assertEquals(Integer.valueOf(-1000), result2.getAmount());

        Money result3 = SEK200.negate();
        assertEquals(Integer.valueOf(-20000), result3.getAmount());

        Money result4 = EUR20.negate();
        assertEquals(Integer.valueOf(-2000), result4.getAmount());

        Money result5 = SEK0.negate();
        assertEquals(Integer.valueOf(0), result5.getAmount());

        Money result6 = EUR0.negate();
        assertEquals(Integer.valueOf(0), result6.getAmount());

        Money result7 = SEKn100.negate();
        assertEquals(Integer.valueOf(10000), result7.getAmount());
    }


    @Test
    public void testCompareTo() {
        // Sprawdza, czy metoda compareTo() porównuje pieniądze poprawnie
        assertEquals(0, SEK100.compareTo(new Money(10000, SEK)));
        assertEquals(0, EUR10.compareTo(new Money(1000, EUR)));
        assertEquals(0, SEK200.compareTo(new Money(20000, SEK)));
        assertEquals(0, EUR20.compareTo(new Money(2000, EUR)));
        assertEquals(0, SEK0.compareTo(new Money(0, SEK)));
        assertEquals(0, EUR0.compareTo(new Money(0, EUR)));
        assertEquals(-1, SEKn100.compareTo(new Money(-5000, SEK))); // Different amount
    }
}
