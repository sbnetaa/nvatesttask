package main.java;

import java.util.Scanner;

import main.java.controllers.CardController;
import main.java.entities.BankCard;
import main.java.entities.BonusCreditCard;
import main.java.entities.BonusDebitCard;
import main.java.entities.CashbackCreditCard;
import main.java.entities.CashbackDebitCard;
import main.java.entities.CreditCard;
import main.java.entities.DebitCard;
import main.java.entities.SavingsCreditCard;
import main.java.entities.SavingsDebitCard;

public class NVATestTask {

	private static CardController controller;
	
	public static void main(String[] args) {

		controller = new CardController();
		try (Scanner scanner = new Scanner(System.in);) {
		chooseClass(scanner);
		}
		}
		
	
	public static void chooseClass(Scanner scanner) {
		BankCard card = null;
		while(card == null) {
			System.out.println("Введите номер пункта с именем класса, объект"
		+ " которого необходимо создать");
			System.out.println("1. CreditCard\n2. DebitCard\n3. BonusDebitCard\n4. BonusCreditCard\n"
		+ "5. CashbackDebitCard\n6. CashbackCreditCard\n7. SavingsDebitCard\n8. SavingsCreditCard\n9. Выход");
			// Даже используя Reflection API не нашел способа обойтись без горы if'ов, так как ругается на несоответствие типов, а привести можно только к BankCard.	
			int numberOfClassName = scanner.nextInt(); 
			scanner.nextLine();
			if (numberOfClassName == 1) {
				card = new CreditCard();
			} else if (numberOfClassName == 2) {
				card = new DebitCard();
			} else if (numberOfClassName == 3) {
				card = new BonusDebitCard();
			} else if (numberOfClassName == 4) {
				card = new BonusCreditCard();
			} else if (numberOfClassName == 5) {
				card = new CashbackDebitCard();
			} else if (numberOfClassName == 6) {
				card = new CashbackCreditCard();
			} else if (numberOfClassName == 7) {
				card = new SavingsDebitCard();
			} else if (numberOfClassName == 8) {
				card = new SavingsCreditCard();
			} else if (numberOfClassName == 9) {
				System.exit(0);
			} else {
				System.out.println("Нет такого пункта: " + numberOfClassName);
			}
		}
		
		
		System.out.println("Введите метод и опционально сумму");
			
		while(scanner.hasNext()) {
			String scannerNext = scanner.nextLine();
			if (scannerNext.equals("switchCard")) break;
			controller.performOperation(scannerNext, card);
		}
		chooseClass(scanner);
	}
}