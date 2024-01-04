package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;



public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	/**
	 * Testuje czy otwieranie konta działa poprawnie
	 * @throws AccountExistsException wyrzuca jeśli konto istnieje
	 * @throws AccountDoesNotExistException wyrzuca jeśli konto nie istnieje
	 */
	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		// Nie działa, powinno wywalić wyjątek, bo już takie konto istnieje w tym banku.
		assertThrows(AccountExistsException.class, () ->{
			SweBank.openAccount("Ulrika");
		});
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		//Wyrzuca NullPointerEception zamiast AccountDoesNotExistException
		assertThrows(AccountDoesNotExistException.class, ()->{
			SweBank.deposit("Testowa", new Money(200, SEK));
		});

		//Twierdzi że takie konto nie istnieje, mimo że zostało stworzone powyżej
		Nordea.deposit("Bob", new Money(400, SEK));
		assertEquals(Integer.valueOf(60), Nordea.getBalance("Bob"));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		assertThrows(AccountDoesNotExistException.class, ()->{
			SweBank.withdraw("Testowa", new Money(200, SEK));
		});

		Nordea.deposit("Bob", new Money(200, SEK));
		Nordea.withdraw("Bob", new Money(200, SEK));
		//Konto nie istnieje... znowu
		//Cannot invoke "b_Money.Account.deposit(b_Money.Money)" because "account" is null
		assertEquals(Integer.valueOf(0), Nordea.getBalance("Bob"));
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertThrows(AccountDoesNotExistException.class, ()->{
			Nordea.getBalance("Test");
		});
		//Konto nie istnieje...
		//Cannot invoke "b_Money.Account.deposit(b_Money.Money)" because "account" is null
		assertEquals(Integer.valueOf(0), Nordea.getBalance("Bob"));
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		assertThrows(AccountDoesNotExistException.class, ()->{
			Nordea.transfer("Bob", DanskeBank, "test", new Money(200, SEK));
		});

		//Konto nie istnieje...
		Nordea.transfer("Bob", SweBank, "Bob", new Money(200,SEK));
		assertEquals(Integer.valueOf(30), SweBank.getBalance("Bob"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		//Sprawdzamy czy wywali błąd dla nieistniejącego konta
		assertThrows(AccountDoesNotExistException.class, ()->{
			Nordea.addTimedPayment("Test", "1", 3,1, new Money(200, SEK), SweBank,"Bob");
		});
		//Dodajemy płatność czasową oraz sprawdzamy czy po upłynięciu początkowej wartości
		//next nasąpi przelew
		Nordea.addTimedPayment("Bob", "1", 3,1, new Money(200, SEK), SweBank,"Bob");
		Nordea.tick();
		assertEquals(Integer.valueOf(30), SweBank.getBalance("Bob"));

		//Usunięcie płatności czasowej i sprawdzenie czy po kolejnych kilku tyknięciach zapłąci czy nie.
		Nordea.removeTimedPayment("Bob","1");
		for (int i = 0; i < 3; i++) {
			Nordea.tick();
		}

		assertEquals(Integer.valueOf(30), SweBank.getBalance("Bob"));

	}
}
