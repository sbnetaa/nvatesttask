package main.java.entities;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public final class BonusCreditCard extends CreditCard implements BonusCard {
	
	private AtomicLong bonusPoints;
	
	public BonusCreditCard() {
		bonusPoints = new AtomicLong(0);
	}
	
	@Override
	public Boolean pay(final long SUM) {
		if (SUM < 0) return false;
		AtomicLong balance = getBalance();
		final long AVAILABLE_BALANCE = getCreditBalance().get() + balance.get();
		if (AVAILABLE_BALANCE < SUM) {
			return false;
		} else {
			if (balance.get() >= SUM) {
				balance.set(balance.get() - SUM);
			} else {
				getCreditBalance().set(AVAILABLE_BALANCE - SUM);
				balance.set(0);
			}
			bonusPoints.set(bonusPoints.get() + (long)(SUM * 0.01));
			return true;
		}
	}
	
	@Override
	public Boolean payWithBonusPoints(long sum) {
		if (sum < 0) return false;
		AtomicLong balance = getBalance();
		final long AVAILABLE_BALANCE = getCreditBalance().get() + balance.get() + bonusPoints.get();
		if (AVAILABLE_BALANCE < sum) {
			return false;
		} else {
			if (sum <= bonusPoints.get()) {			
			bonusPoints.set(bonusPoints.get() - sum);
			sum = 0;
			return true;
			} else {
				sum -= bonusPoints.get();
				bonusPoints.set(0);
			}
			
			if (balance.get() >= sum) {
				balance.set(balance.get() - sum);
			} else {
				getCreditBalance().set(getCreditBalance().get() + balance.get() + bonusPoints.get() - sum);
				balance.set(0);
			}
			bonusPoints.set(bonusPoints.get() + (long)(sum * 0.01));
			return true;
		}
	}
	
	@Override
	public Map<String, Long> getAvailableFunds() {
		return Map.of("баланс", getBalance().get(), "кредитный баланс", getCreditBalance().get(), "кредитный лимит"
				, getCreditLimit(), "бонусные баллы", bonusPoints.get());
	}
}
