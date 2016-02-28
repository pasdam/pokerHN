package eu.reply.pokerbot;

public class Card {
	private int value;
	private int suit;
	
	public Card(int value, int suit) {
		this.value = value;
		this.suit = suit;
	}
	
	public Card() {
		
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getSuit() {
		return suit;
	}
	public void setSuit(int suit) {
		this.suit = suit;
	}
	
	
	
	
}
