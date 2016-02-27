package eu.reply.pokerbot;

public class HandEvent {
	public int handPhase;
	public int previousActionDone;
	public int currentOpponentAction;
	public int pot;
	public int turnPosition;
	public Card[] playerCards;
	public Card[] communityCards;
	
	public HandEvent(){
		
	}
	
	public HandEvent(int handPhase, int previousBetType, int currentBetType, int pot, int turnPosition,
			Card[] playerCards, Card[] communityCards) {
		super();
		this.handPhase = handPhase;
		this.previousActionDone = previousBetType;
		this.currentOpponentAction = currentBetType;
		this.pot = pot;
		this.turnPosition = turnPosition;
		this.playerCards = playerCards;
		this.communityCards = communityCards;
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

	public void setPreviousActionDone(int previousBetType) {
		this.previousActionDone = previousBetType;
	}

	public int getOpponentActionDone() {
		return currentOpponentAction;
	}

	public void setOpponentActionDone(int currentBetType) {
		this.currentOpponentAction = currentBetType;
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
	
}
