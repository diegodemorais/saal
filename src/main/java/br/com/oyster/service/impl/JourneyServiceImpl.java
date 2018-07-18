package br.com.oyster.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.oyster.model.Card;
import br.com.oyster.model.Region;
import br.com.oyster.model.Station;
import br.com.oyster.model.Trip;
import br.com.oyster.model.Trip.TransportType;
import br.com.oyster.repository.CardRepository;
import br.com.oyster.repository.JourneyRepository;
import br.com.oyster.service.JourneyService;


@Service
public class JourneyServiceImpl implements JourneyService{
	
    private final JourneyRepository journeyRepository;
	
	// Fares
	private static final Double MAX = 3.20d;
	private static final Double IN_ZONE1 = 2.50d;
	private static final Double OUT_ZONE1 = 2.00d;
	private static final Double TWO_WITH_ZONE1 = 3.00d;
	private static final Double TWO_WITHOUT_ZONE1 = 2.25d;
	private static final Double THREE_ZONES = 3.20d;
	private static final Double BUS = 3.20d;
	private static final Double NONE = 0d;
	
	@Autowired
	public JourneyServiceImpl(JourneyRepository journeyRepository) {
		this.journeyRepository = journeyRepository;
	}

	@Override
	public boolean enterStation(Card card) {
		if (card.hasBalanceEnough(MAX)) {
			card.chargeFare(MAX);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void exitStation(Card card) {
		double fare = calculateFare(journeyRepository.discountTrips(card));
		card.loadMoney(fare);
	}

	@Override
	public boolean takeTripByBus(Card card, Region from, Region to) {
		if (card.hasBalanceEnough(BUS)) {
			journeyRepository.addTrip(card, new Trip(from, to, TransportType.BUS));
			card.chargeFare(BUS);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void takeTripByTube(Card card, Region from, Region to) {
		journeyRepository.addTrip(card, new Trip(from, to, TransportType.TUBE, false));
	}
	
	@Override
	public void printTrips(Card card) {
		List<Trip> trips = journeyRepository.getTrips(card);
		if (trips == null ) {
			System.out.println("No trip done on this card.");
			return;
		}
		for(Trip trip : trips){
			System.out.println(trip.getTransportType() == TransportType.BUS ? "Bus" : "Tube");
			System.out.println(" from " + trip.getFrom().getName() 
					+ " to" + trip.getTo().getName());
		}
	}

	@Override
	public double calculateFare(List<Trip> trips) {
		int zonesQuantity = 0;
		boolean zone1 = false;
		for(Trip trip : trips) {
			try {
				Station from = (Station)trip.getFrom();
				Station to = (Station)trip.getTo();
				int fromZone = from.getZone();
				int toZone = to.getZone();
				if (toZone == 1) {
					zone1 = true;
				}
				zonesQuantity += fromZone > toZone ? fromZone - toZone : toZone - fromZone;	
			} catch (Exception e) {
				System.err.println("Error on cast. Why is a instance of Region here?");
				return NONE;
			}
			switch (zonesQuantity) {
				case 0: return NONE;
				case 1: return zone1 ? IN_ZONE1 : OUT_ZONE1;
				case 2: return zone1 ? TWO_WITH_ZONE1 : TWO_WITHOUT_ZONE1;
				case 3: default: return THREE_ZONES;
			}
		}
		return NONE; 
	}

}
