package br.com.oyster.model;

public class Station extends Region{

	private final int zone;
	
	public Station(String name, int zone) {
		super(name);
		this.zone = zone;
	}
	
	public int getZone() {
		return this.zone;
	}

}
