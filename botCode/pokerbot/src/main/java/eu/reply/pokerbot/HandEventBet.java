package eu.reply.pokerbot;

public class HandEventBet {
	public int handPhase;
	public int previousActionDone;
	public int currentOpponentAction;
	public int pot;
	public int turnPosition;
	public Card[] playerCards;
	public Card[] communityCards;
	public int currentActionDone;
	
	public HandEventBet() {
		
	}
	
	
	public HandEventBet(int handPhase, int previousActionDone, int currentOpponentDone, int pot, int turnPosition,
			Card[] playerCards, Card[] communityCards, int currentActionDone) {
		super();
		this.handPhase = handPhase;
		this.previousActionDone = previousActionDone;
		this.currentOpponentAction = currentOpponentDone;
		this.pot = pot;
		this.turnPosition = turnPosition;
		this.playerCards = playerCards;
		this.communityCards = communityCards;
		this.currentActionDone = currentActionDone;
	}


	public int getHandPhase() {
		return handPhase;
	}
	public void setHandPhase(int handPhase) {
		this.handPhase = handPhase;
	}
	public int getPreviousActionDone() {
		return previousActionDone;
	}
	public void setPreviousActionDone(int previousActionDone) {
		this.previousActionDone = previousActionDone;
	}
	public int getCurrentOpponentDone() {
		return currentOpponentAction;
	}
	public void setCurrentOpponentDone(int currentOpponentDone) {
		this.currentOpponentAction = currentOpponentDone;
	}
	public int getPot() {
		return pot;
	}
	public void setPot(int pot) {
		this.pot = pot;
	}
	public int getTurnPosition() {
		return turnPosition;
	}
	public void setTurnPosition(int turnPosition) {
		this.turnPosition = turnPosition;
	}
	public Card[] getPlayerCards() {
		return playerCards;
	}
	public void setPlayerCards(Card[] playerCards) {
		this.playerCards = playerCards;
	}
	public Card[] getCommunityCards() {
		return communityCards;
	}
	public void setCommunityCards(Card[] communityCards) {
		this.communityCards = communityCards;
	}
	public int getCurrentActionDone() {
		return currentActionDone;
	}
	public void setCurrentActionDone(int currentActionDone) {
		this.currentActionDone = currentActionDone;
	}
	
	
	
}
