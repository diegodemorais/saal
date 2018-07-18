package br.com.oyster;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.oyster.model.Card;
import br.com.oyster.model.Region;
import br.com.oyster.model.Station;
import br.com.oyster.service.CardService;
import br.com.oyster.service.JourneyService;

@Component
public class ConsoleRunner implements CommandLineRunner {

	private final CardService cardService;
	private final JourneyService journeyService;
	private List<Region> mockRegions = new ArrayList<>();

	@Autowired
	public ConsoleRunner(CardService cardService, JourneyService journeyService) {
		this.cardService = cardService;
		this.journeyService = journeyService;

		mockRegions.add(new Station("Holborn", 1));
		mockRegions.add(new Station("Earl's Court", 1));
		mockRegions.add(new Station("Earl's Court", 2));
		mockRegions.add(new Station("Hammersmith", 2));
		mockRegions.add(new Station("Wimbledon", 3));

		mockRegions.add(new Region("Chelsea"));
		mockRegions.add(new Region("MaryLand"));
		mockRegions.add(new Region("Bethnal Green"));
		mockRegions.add(new Region("Stamford Hill"));
		mockRegions.add(new Region("Hoxton"));

	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Insert card code: ");
		String code = scanner.nextLine();

		System.out.println("Insert card initial balance: ");
		Double value = scanner.nextDouble();

		Card card;
		if (value != null && value >0D) {
			card = cardService.createCard(code, value);	
		} else {
			card = cardService.createCard(code);
		}

		int menu = -1, from =-1, to =-1;
		String enterStation;
		mainMenu: while(true) {
			menu = -1;
			from = -1;
			to = -1;
			enterStation = null;
			System.out.println("1 - Take a trip");
			System.out.println("2 - Show balance");
			System.out.println("3 - Show journey");
			System.out.println("4 - Exit");
			System.out.print("Option: ");
			menu = scanner.nextInt();
			switch(menu) {
			case 1:
				System.out.println("Where are you?");
				for(int i=0 ; i < mockRegions.size() ; i++) {
					System.out.println(i+ " - " + mockRegions.get(i).getName());
				}
				from = scanner.nextInt();
				if (mockRegions.get(from) instanceof Station) {
					System.out.print("Do you want to enter in the Station to go by tube? (Y/N): " );
					enterStation= scanner.next();
					if (enterStation.toUpperCase().equals("Y")) {
						if (journeyService.enterStation(card)) {
							System.out.println("Insert the destination Station: " );
							for(int i=0 ; i < mockRegions.size() ; i++) {
								if (i != from && mockRegions.get(i) instanceof Station) {
									System.out.println(i+ " - " + mockRegions.get(i).getName());
								}
							}
							to = scanner.nextInt();
							journeyService.takeTripByTube(card, mockRegions.get(from), mockRegions.get(to));
							continue mainMenu;
						} else {
							System.out.println("Your balance (£ " + card.getBalance() + ") is not enough to this trip.");
						}
					} 
				}
				System.out.println("Where do you want to go by bus?");                			
				for(int i=0 ; i < mockRegions.size() ; i++) {
					System.out.println(i+ " - " + mockRegions.get(i).getName());
				}
				to = scanner.nextInt();
				if (journeyService.takeTripByBus(card, mockRegions.get(from), mockRegions.get(to))) {
					continue mainMenu;	
				} else {
					System.out.println("Your balance (£ " + card.getBalance() + ") is not enough to this trip.");
					return;
				}
			case 2:
				System.out.println("Current balance: £" + card.getBalance()); 
				break;
			case 3:
				System.out.println("Journey: ");
				journeyService.printTrips(card);
				break;
			case 4:
				return;
			default:
				continue mainMenu;
			}	
		}
	}
}