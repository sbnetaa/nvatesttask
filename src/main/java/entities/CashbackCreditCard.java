package main.java.entities;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public final class CashbackCreditCard extends CreditCard implements CashbackCard {
	
	private AtomicLong cashback;
	
	public CashbackCreditCard() {
		cashback = new AtomicLong(0);
	}
	
		@Override
		public Boolean pay(final long SUM) {
			if (SUM < 0) return false;
			AtomicLong creditBalance = getCreditBalance();
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
				if (SUM >= 5000) cashback.set(cashback.get() + (long)(SUM * 0.05));
				return true;
			}
		}
		
		@Override
		public Map<String, Long> getAvailableFunds() {
			return Map.of("баланс", getBalance().get(), "кредитный баланс", getCreditBalance().get()
					, "кредитный лимит", getCreditLimit(), "кэшбэк", cashback.get());
		}
	}
