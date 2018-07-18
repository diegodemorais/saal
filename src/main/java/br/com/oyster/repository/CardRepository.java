package br.com.oyster.repository;


import org.springframework.stereotype.Component;

import br.com.oyster.model.Card;

import java.util.HashMap;
import java.util.Map;

@Component
public class CardRepository {
    private Map<String, Card> cards = new HashMap<>();

    public Card getCard(String cardCode) {
		Card card = cards.get(cardCode);
        return card;
    }

    public Card newCard(Card card) {
        cards.put(card.getCode(), card);
        return card;
    }
    
}
