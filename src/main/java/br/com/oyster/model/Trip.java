package br.com.oyster.model;

public class Trip {
	private final Region from;
	private final Region to;
	private final TransportType transportType;
	private boolean discounted;
	
	public Trip(Region from, Region to, TransportType transportType, boolean discounted) {
		this.from = from;
		this.to = to;
		this.transportType = transportType;
		this.discounted = discounted;
	}
	
	public Trip(Region from, Region to, TransportType transportType) {
		this(from, to, transportType, true);
	}
	
	public Region getFrom() {
		return this.from;
	}
	
	public Region getTo() {
		return this.to;
	}
	
	public boolean isDiscounted() {
		return this.discounted;
	}
	
	public void setDiscounted() {
		this.discounted = true;
	}
	
	public TransportType getTransportType() {
		return this.transportType;
	}
	
	public static enum TransportType {
		BUS, TUBE;
	}
	
}
