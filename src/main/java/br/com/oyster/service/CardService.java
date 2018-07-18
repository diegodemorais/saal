package br.com.oyster.service;

import br.com.oyster.model.Card;

public interface CardService {
    Card getCard(String code);
    Card createCard(String code);
    Card createCard(String code, double balance);
}
