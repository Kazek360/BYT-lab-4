package b_Money;

import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000, SEK));

		SweBank.deposit("Alice", new Money(10000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		//Cannot invoke "b_Money.Account.deposit(b_Money.Money)" because "account" is null
		testAccount.addTimedPayment("test", 2, 1, new Money(100, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("test"));

		testAccount.removeTimedPayment("test");
		assertFalse(testAccount.timedPaymentExists("test"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		//Cannot invoke "b_Money.Account.deposit(b_Money.Money)" because "account" is null
		testAccount.addTimedPayment("test2", 2, 1, new Money(100, SEK), SweBank, "Alice");

		testAccount.tick(); // Simulate one time unit

		assertEquals(Integer.valueOf(10200), testAccount.getBalance().getAmount());
	}

	@Test
	public void testAddWithdraw() {
		//Cannot invoke "b_Money.Account.deposit(b_Money.Money)" because "account" is null
		testAccount.deposit(new Money(200, SEK));
		assertEquals(Integer.valueOf(10400), testAccount.getBalance().getAmount());

		testAccount.withdraw(new Money(200, SEK));
		assertEquals(Integer.valueOf(10200), testAccount.getBalance().getAmount());
	}
	
	@Test
	public void testGetBalance() {
		//Cannot invoke "b_Money.Account.deposit(b_Money.Money)" because "account" is null
		assertEquals(Integer.valueOf(10000), testAccount.getBalance().getAmount());	}
}
