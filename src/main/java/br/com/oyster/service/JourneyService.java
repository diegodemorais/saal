package br.com.oyster.service;

import java.util.List;

import br.com.oyster.model.Card;
import br.com.oyster.model.Region;
import br.com.oyster.model.Trip;

public interface JourneyService {
	
	boolean enterStation(Card card);
	void exitStation(Card card);
	
	boolean takeTripByBus(Card card, Region from, Region to);
	void takeTripByTube(Card card, Region from, Region to);
	String tripsToString(Card card);
	
	double calculateFare(List<Trip> trips);

}
