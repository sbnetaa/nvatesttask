package main.java.entities;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class CreditCard extends BankCard {

	private static final long CREDIT_LIMIT = 10000;
	private AtomicLong creditBalance;
	
	public CreditCard() {
		creditBalance = new AtomicLong(CREDIT_LIMIT);
	}
	
	public AtomicLong getCreditBalance() {
		return creditBalance;
	}

	public static long getCreditLimit() {
		return CREDIT_LIMIT;
	}

	@Override
	public AtomicLong getBalance() {
		return super.getBalance();
	}

	@Override
	public void deposit(final long SUM) {
		if (SUM < 0) throw new IllegalArgumentException("Сумма не может быть отрицательной");
		AtomicLong balance = getBalance();
		if (creditBalance.get() == CREDIT_LIMIT) { 
			balance.set(balance.get() + SUM);
		} else if (SUM < CREDIT_LIMIT - creditBalance.get()) {
			creditBalance.set(creditBalance.get() + SUM);
		} else {
			balance.set(creditBalance.get() + SUM - CREDIT_LIMIT);
			creditBalance.set(CREDIT_LIMIT);
		}
	}
	
	@Override
	public Boolean pay(final long SUM) {
		if (SUM < 0) return false;
		AtomicLong balance = getBalance();
		final long AVAILABLE_BALANCE = creditBalance.get() + balance.get();
		if (AVAILABLE_BALANCE < SUM) {
			return false;
		} else {
			if (balance.get() >= SUM) {
				balance.set(balance.get() - SUM);
			} else {
				creditBalance.set(AVAILABLE_BALANCE - SUM);
				balance.set(0);
			}
			return true;
		}
	}
	
	@Override
	public Map<String, Long> getAvailableFunds() {
		return Map.of("баланс", getBalance().get(), "кредитный баланс", creditBalance.get(), "кредитный лимит", CREDIT_LIMIT);
	}
}
