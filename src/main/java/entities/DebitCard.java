package main.java.entities;

import java.util.concurrent.atomic.AtomicLong;

public class DebitCard extends BankCard {

	public DebitCard(){}
	
	@Override
	final public AtomicLong getBalance() {
		return super.getBalance();
	}

	@Override
	public void deposit(final long SUM) {
		if (SUM < 0) throw new IllegalArgumentException("Сумма не может быть отрицательной");
		AtomicLong balance = getBalance();
		balance.set(balance.get() + SUM);
	}
}
