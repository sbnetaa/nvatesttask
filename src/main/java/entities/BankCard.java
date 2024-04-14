package main.java.entities;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public abstract class BankCard {

	private static AtomicLong idGenerator = new AtomicLong(0);
	private final AtomicLong ID;
	private AtomicLong balance;
	
	protected BankCard(){
		idGenerator.set(idGenerator.get() + 1);
		ID = idGenerator;
		balance = new AtomicLong(0);
	}

	public static AtomicLong getIdGenerator() {
		return idGenerator;
	}

	public static void setIdGenerator(final AtomicLong idGenerator) {
		BankCard.idGenerator = idGenerator;
	}

	public AtomicLong getBalance() {
		return balance;
	}

	public AtomicLong getID() {
		return ID;
	}
	
	public Boolean pay(final long SUM) {
		if (SUM < 0) return false;
		if (SUM <= balance.get()) {
			balance.set(balance.get() - SUM);
			return true;		
		}
		return false;
	}
	
	public abstract void deposit(final long SUM);
	
	public Map<String, Long> getAvailableFunds(){
		return Map.of("баланс", balance.get());
	}
}
