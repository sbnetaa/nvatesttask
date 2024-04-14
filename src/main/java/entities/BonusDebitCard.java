package main.java.entities;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public final class BonusDebitCard extends DebitCard implements BonusCard {
	
	private AtomicLong bonusPoints;
	
	public BonusDebitCard() {
		bonusPoints = new AtomicLong(0);
	}
	
	@Override
	public Boolean pay(final long SUM) {
		if (SUM < 0) return false;
		AtomicLong balance = getBalance();
		if (SUM <= balance.get()) {
			balance.set(balance.get() - SUM);
			bonusPoints.set(bonusPoints.get() + (long)(SUM * 0.01));
			return true;		
		}
		return false;
	}
	
	@Override
	public Boolean payWithBonusPoints(final long SUM) {
		if (SUM < 0) return false;
		AtomicLong balance = getBalance();
		if (bonusPoints.get() > SUM) {
			bonusPoints.set(bonusPoints.get() - SUM);
		} else {
			if (SUM <= balance.get()) {
				balance.set(balance.get() - SUM + bonusPoints.get());
				bonusPoints.set((long)(SUM * 0.01));				
			}
			return true;		
		}
		return false;
	}
	
	public Map<String, Long> getAvailableFunds(){
		return Map.of("баланс", getBalance().get(), "бонусные баллы", bonusPoints.get());
	}
}
