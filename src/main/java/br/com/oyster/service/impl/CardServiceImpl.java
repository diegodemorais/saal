package br.com.oyster.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.oyster.model.Card;
import br.com.oyster.repository.CardRepository;
import br.com.oyster.service.CardService;

@Service
public class CardServiceImpl implements CardService {
	
    private static final double INITIAL_BALANCE = 30.0D;
    private final CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

	@Override
	public Card getCard(String code) {
        return cardRepository.getCard(code);
	}

	@Override
	public Card createCard(String code) {
		 return this.createCard(code, INITIAL_BALANCE);
	}
	
	@Override
	public Card createCard(String code, double balance) {
		 Card card = new Card(code, balance);
		 return cardRepository.newCard(card);
	}

}
