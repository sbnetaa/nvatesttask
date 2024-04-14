package main.java.entities;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public final class SavingsCreditCard extends CreditCard implements SavingsCard {

	private AtomicLong savings;
	
	public SavingsCreditCard() {
		savings = new AtomicLong(0);
	}

	@Override
	public void deposit(final long SUM) {
		if (SUM < 0) throw new IllegalArgumentException("Сумма не может быть отрицательной");
		AtomicLong balance = getBalance();
		AtomicLong creditBalance = getCreditBalance();
		final long CREDIT_LIMIT = getCreditLimit();
		if (creditBalance.get() == CREDIT_LIMIT) { 
			balance.set(balance.get() + SUM);
		} else if (SUM < CREDIT_LIMIT - creditBalance.get()) {
			creditBalance.set(creditBalance.get() + SUM);
		} else {
			balance.set(creditBalance.get() + SUM - CREDIT_LIMIT);
			creditBalance.set(CREDIT_LIMIT);
		}
		savings.set(savings.get() + (long)(SUM * 0.00005));
	}
	
	@Override
	public Boolean payWithSavings(long sum) {
		if (sum < 0) return false;
		AtomicLong balance = getBalance();
		AtomicLong creditBalance = getCreditBalance();
		final long AVAILABLE_BALANCE = creditBalance.get() + balance.get() + savings.get();
		if (AVAILABLE_BALANCE < sum) {
			return false;
		} else {
			if (savings.get() >= sum) {
				savings.set(savings.get() - sum);
				sum = 0;
			} else {
				sum -= savings.get();
				savings.set(0);
			}
			
			if (balance.get() >= sum) {
				balance.set(balance.get() - sum);
			} else {
				creditBalance.set(AVAILABLE_BALANCE - sum);
				balance.set(0);
			}
			return true;
		}	
	}
	
	@Override
	public Map<String, Long> getAvailableFunds() {
		return Map.of("баланс", getBalance().get(), "кредитный баланс", getCreditBalance().get()
				, "кредитный лимит", getCreditLimit(), "накопления", savings.get());
	}
}
