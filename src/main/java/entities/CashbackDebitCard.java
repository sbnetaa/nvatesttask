package main.java.entities;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public final class CashbackDebitCard extends DebitCard implements CashbackCard {

	private AtomicLong cashback;
	
	public CashbackDebitCard() {
		cashback = new AtomicLong(0);
	}

	
	public AtomicLong getCashback() {
		return cashback;
	}

	public void setCashback(AtomicLong cashback) {
		this.cashback = cashback;
	}

	public Boolean pay(final long SUM) {
		if (SUM < 0) return false;
		AtomicLong balance = getBalance();
		if (SUM <= balance.get()) {
			balance.set(balance.get() - SUM);
			if (SUM >= 5000) cashback.set(cashback.get() + (long)(SUM * 0.05));
			return true;		
		}
		return false;
	}
	
	public Map<String, Long> getAvailableFunds(){
		return Map.of("баланс", getBalance().get(), "кэшбэк", cashback.get());
	}
}
