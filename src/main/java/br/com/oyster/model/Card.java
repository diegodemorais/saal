package br.com.oyster.model;

import java.security.InvalidParameterException;

public class Card {
    private final String code;
    private Double balance;

    public Card(String code, Double balance) {
    	this.code = code;
        this.balance = balance;
    }

    public String getCode() {
        return this.code;
    }

    public Double getBalance() {
        return this.balance;
    }

    public Double loadMoney(Double money) throws InvalidParameterException {
    	checkPositiveNumber(money);
    	balance += money;
    	return balance;
    }
    
    public Double chargeFare(Double fare) throws InvalidParameterException {
    	checkPositiveNumber(fare);
        balance -= fare;
        return balance;
    }
    
    public boolean hasBalanceEnough(Double fare) throws InvalidParameterException {
    	checkPositiveNumber(fare);
        return (getBalance() >= fare);
    }
    
    
    private void checkPositiveNumber(Double value) throws InvalidParameterException{
        if(Double.isNaN(value) || value < 0d) {
            throw new InvalidParameterException("The value must be positive (Ex: 30.5)");
        }
    }
}
