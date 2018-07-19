package br.com.oyster;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.oyster.model.Card;
import br.com.oyster.model.Station;
import br.com.oyster.service.CardService;
import br.com.oyster.service.JourneyService;
import br.com.oyster.service.RegionService;

@Component
public class ConsoleRunner implements CommandLineRunner {

	private static final String STATION1 = "Holborn";
	private static final String STATION2 = "Earl's Court";
	private static final String STATION3 = "Earl's Court";
	private static final String STATION4 = "Hammersmith";
	private static final String STATION5 = "Wimbledon";
	
	private static final String REGION1 = "Chelsea";
	private static final String REGION2 = "MaryLand";
	private static final String REGION3 = "Bethnal Green";
	private static final String REGION4 = "Stamford Hill";
	private static final String REGION5 = "Hoxton";
	
	private final CardService cardService;
	private final JourneyService journeyService;
	private final RegionService regionService;
	

	@Autowired
	public ConsoleRunner(CardService cardService, JourneyService journeyService, RegionService regionService) {
		this.cardService = cardService;
		this.journeyService = journeyService;
		this.regionService = regionService;

		regionService.createStation(STATION1, 1);
		regionService.createStation(STATION2, 1);
		regionService.createStation(STATION3, 2);
		regionService.createStation(STATION4, 2);
		regionService.createStation(STATION5, 3);

		regionService.createRegion(REGION1);
		regionService.createRegion(REGION2);
		regionService.createRegion(REGION3);
		regionService.createRegion(REGION4);
		regionService.createRegion(REGION5);
	}
	
	private String regionConstants(int num) {
		switch (num) {
			case 0: return STATION1;
			case 1: return STATION2;
			case 2: return STATION4;
			case 3: return STATION5;
			case 4: return REGION1;
			case 5: return REGION2;
			case 6: return REGION3;
			case 7: return REGION4;
			case 8: return REGION5;
			default: return "";
		}
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
		String enterStation, anotherTrip, fromStr, toStr;
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
				regionService.printRegions(false);
				from = scanner.nextInt();
				fromStr = regionConstants(from);
				if (regionService.getRegion(fromStr) instanceof Station) {
					System.out.print("Do you want to enter in the Station to go by tube? (Y/N): " );
					enterStation= scanner.next();
					if (enterStation.toUpperCase().equals("Y")) {
						if (journeyService.enterStation(card)) {
							while(true) {
								System.out.println("Insert the destination Station: " );
								regionService.printRegions(true);
								to = scanner.nextInt();
								toStr = regionConstants(to);				
								journeyService.takeTripByTube(card, regionService.getRegion(fromStr), regionService.getRegion(toStr));
								System.out.print("Do you want to go to another Station by tube? (Y/N): " );
								anotherTrip= scanner.next();
								if (anotherTrip.toUpperCase().equals("Y")) {
									fromStr = toStr;
								} else {
									journeyService.exitStation(card);
									continue mainMenu;
								}
							}
						} else {
							System.out.println("Your balance (£ " + card.getBalanceFormatted() + ") is not enough to this trip.");
						}
					} 
				}
				System.out.println("Where do you want to go by bus?");                			
				regionService.printRegions(false);
				to = scanner.nextInt();
				toStr = regionConstants(to);
				if (journeyService.takeTripByBus(card,  regionService.getRegion(fromStr), regionService.getRegion(toStr))) {
					continue mainMenu;	
				} else {
					System.out.println("Your balance (£ " + card.getBalanceFormatted() + ") is not enough to this trip.");
					return;
				}
			case 2:
				System.out.println("Current balance: £" + card.getBalanceFormatted()); 
				break;
			case 3:
				System.out.println("Journey: ");
				System.out.println(journeyService.tripsToString(card));
				break;
			case 4:
				return;
			default:
				continue mainMenu;
			}	
		}
	}
}