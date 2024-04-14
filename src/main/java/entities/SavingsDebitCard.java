package main.java.entities;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public final class SavingsDebitCard extends DebitCard implements SavingsCard{
	
	private AtomicLong savings;
	
	public SavingsDebitCard() {
		savings = new AtomicLong(0);
	}

	@Override
	public void deposit(final long SUM) {
		if (SUM < 0) throw new IllegalArgumentException("Сумма не может быть отрицательной");
		AtomicLong balance = getBalance();
		balance.set(balance.get() + SUM);
		savings.set(savings.get() + (long)(SUM * 0.00005));
	}
	
	@Override
	public Boolean payWithSavings(long sum) {
		if (sum < 0) return false;
		AtomicLong balance = getBalance();
		final long AVAILABLE_BALANCE = balance.get() + savings.get();
		if (AVAILABLE_BALANCE < sum) return false;
		
		if (savings.get() >= sum) {
			savings.set(savings.get() - sum);
			sum = 0;
		} else {
			sum -= savings.get();
			savings.set(0);
		}
			balance.set(balance.get() - sum);
			return true;
	}
	
	public Map<String, Long> getAvailableFunds(){
		return Map.of("баланс", getBalance().get(), "накопления", savings.get());
	}
}
