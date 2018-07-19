package br.com.oyster.oyster.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.com.oyster.model.Card;
import br.com.oyster.repository.CardRepository;
import br.com.oyster.repository.JourneyRepository;
import br.com.oyster.repository.RegionRepository;
import br.com.oyster.service.CardService;
import br.com.oyster.service.JourneyService;
import br.com.oyster.service.RegionService;
import br.com.oyster.service.impl.CardServiceImpl;
import br.com.oyster.service.impl.JourneyServiceImpl;
import br.com.oyster.service.impl.RegionServiceImpl;

public class JourneyServiceTests {

	private static final String CARD_CODE = "DIEGO999";
	private static final double INITIAL_VALUE = 30D;
	
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

	
	private CardService cardService;
	private JourneyService journeyService;
	private RegionService regionService;

	@Before
	public void setUp() {
		cardService = new CardServiceImpl(new CardRepository());
		journeyService = new JourneyServiceImpl(new JourneyRepository());
		regionService = new RegionServiceImpl(new RegionRepository());
		
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

	@Test
	public void TestAllMethods() {
		
		String code = CARD_CODE;
		Double value = INITIAL_VALUE;
		Card card = cardService.createCard(code, value);
		Double expected = value;
		assertEquals(expected, card.getBalance());
		
		String from = STATION1;
		boolean entered = journeyService.enterStation(card);
		assertTrue(entered);
		
		expected = expected - JourneyServiceImpl.MAX;
		assertEquals(expected, card.getBalance());

		String to = STATION2;
		journeyService.takeTripByTube(card, regionService.getRegion(from), regionService.getRegion(to));
		from = to;
		to = STATION5;
		journeyService.takeTripByTube(card, regionService.getRegion(from), regionService.getRegion(to));
		journeyService.exitStation(card);
		
		expected = expected + JourneyServiceImpl.MAX - JourneyServiceImpl.TWO_WITHOUT_ZONE1;
		assertEquals(expected, card.getBalance());
		
		from = REGION1;
		to = REGION5;
		journeyService.takeTripByBus(card, regionService.getRegion(from), regionService.getRegion(to));
		expected = expected - JourneyServiceImpl.BUS;
		assertEquals(expected, card.getBalance());
		
		String tripsActual = journeyService.tripsToString(card);
		String tripsExpected = "Tube from Holborn to Earl's Court" + System.lineSeparator()
				 + "Tube from Earl's Court to Wimbledon" + System.lineSeparator()
				 + "Bus from Chelsea to Hoxton" + System.lineSeparator();
		assertEquals(tripsActual, tripsExpected);
		
	}
	
}
