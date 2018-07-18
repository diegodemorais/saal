package br.com.oyster.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import br.com.oyster.model.Card;
import br.com.oyster.model.Region;
import br.com.oyster.model.Trip;

@Component
public class JourneyRepository {
	private Map<Card, List<Trip>> journeys = new HashMap<>();

	public void addTrip(Card card, Trip trip) {
		List<Trip> trips = this.journeys.get(card);
		if (trips == null) {
			trips = new ArrayList<>();
			this.journeys.put(card, trips);
		}
		trips.add(trip);
	}

	public List<Trip> getTrips(Card card) {
		return journeys.get(card);
	}

	public List<Trip> discountTrips(Card card) {
		return discountTrips(card, true);
	}

	public List<Trip> getTripsToDiscount(Card card) {
		return discountTrips(card, false);
	}

	private List<Trip> discountTrips(Card card, boolean discount) {
		List<Trip> tripsToDiscount = new ArrayList<Trip>();
		for(Trip trip : journeys.get(card)) {
			if (!trip.isDiscounted()) {
				if (discount) trip.setDiscounted();
				tripsToDiscount.add(trip);
			}
		}
		return tripsToDiscount;
	}
}
