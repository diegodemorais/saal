package br.com.oyster.oyster.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.oyster.repository.CardRepository;
import br.com.oyster.service.CardService;
import br.com.oyster.service.impl.CardServiceImpl;

public class CardServiceTests {

	private static final String CODE = "ABC123";
	private static final double BALANCE = 30D;
	private CardService cardService;

	@Before
	public void setUp() {
		cardService = new CardServiceImpl(new CardRepository());
	}

	@Test
	public void TestAllMethods() {
		cardService.createCard(CODE, BALANCE);
		
		String expectedCode = CODE;
		assertEquals(expectedCode, cardService.getCard(CODE).getCode());
		
		double expectedBalance = BALANCE;
		assertEquals(expectedBalance, cardService.getCard(CODE).getBalance().doubleValue(), 0.01D);
	}


}
