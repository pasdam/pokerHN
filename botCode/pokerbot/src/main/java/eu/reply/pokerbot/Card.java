package eu.reply.pokerbot;

public class Card {
	private String value;
	private String suit;
	
	public Card(String value, String suit) {
		this.value = value;
		this.suit = suit;
	}
	
	public Card() {
		
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getSuit() {
		return suit;
	}
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	
	
	
}
