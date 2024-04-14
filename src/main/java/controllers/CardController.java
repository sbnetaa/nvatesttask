package main.java.controllers;

import java.util.Map;

import main.java.entities.BankCard;
import main.java.entities.BonusCard;
import main.java.entities.SavingsCard;

public class CardController {


	public CardController() {}
	
	public void performOperation(String stringFromScanner, BankCard card) {
		boolean operationCompleted = true;
		if (stringFromScanner.contains("getBalance")) {
			System.out.println(card.getBalance().get());
		} else if (stringFromScanner.contains("getAvailableFunds")) {
			for (Map.Entry<String, Long> entry : card.getAvailableFunds().entrySet()) 
				System.out.println(entry.getKey() + ": " + entry.getValue());			
		} else if (stringFromScanner.contains(" ")) {
			String[] operationAndSum = stringFromScanner.split(" ");
			if (operationAndSum[0].equals("deposit")) {
				card.deposit(Long.valueOf(operationAndSum[1]));
			} else if (operationAndSum[0].equals("pay")) {			
				operationCompleted = card.pay(Long.valueOf(operationAndSum[1]));
			} else if (operationAndSum[0].equals("payWithSavings")) {
				if (card instanceof SavingsCard) {
					operationCompleted = ((SavingsCard)card).payWithSavings(Long.valueOf(operationAndSum[1]));
				} else {
					System.out.println("Такая операция недоступна для данной карты");
					return;
				}
			} else if (operationAndSum[0].equals("payWithBonusPoints")) {
				if (card instanceof BonusCard) {
					operationCompleted = ((BonusCard)card).payWithBonusPoints(Long.valueOf(operationAndSum[1]));
				} else {
					System.out.println("Такая операция недоступна для данной карты");
					return;
				}
			}
			if (operationCompleted)	System.out.println("Операция выполнена");
			else System.out.println("Недостаточно средств или введена отрицательная сумма");
		} else {
			System.out.println("Нет такой операции");
		}
	
	}
}
