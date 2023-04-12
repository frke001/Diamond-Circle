package org.unibl.etf.cards;

public abstract class Card {
	
	protected Integer numberOfFields;
	
	public Card() {
		numberOfFields = 0;
	}
	public Card(Integer numberOfFileds) {
		this.numberOfFields = numberOfFileds;
	}
	public Integer getNumberOfFileds() {
		return numberOfFields;
	}
	public void setNumberOfFileds(Integer numberOfFileds){
		this.numberOfFields = numberOfFileds;
	}
}
