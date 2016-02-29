package eu.reply.pokerbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import eu.reply.pokerbot.AbstractBotReceiver.SNAP;



public abstract class AbstractBotReceiver implements Runnable{
	public enum BET_TYPE {
		NONE,
		FOLD,
		CHECK,
		CALL,
		BET,
		RAISE
	};

	public enum SUITS {
		Hearts('h'),
		Diamonds('d'),
		Club('c'),
		Spades('s');
		
		private char suit;
		
		SUITS(char suit){
			this.suit = suit;
		}
		
		public static SUITS evalute(char suit){
			for(SUITS aSuit: SUITS.values()){
				if(aSuit.suit == suit){
					return aSuit;
				}
			}
			throw new IllegalArgumentException("Suit not found:"+suit);
		}
	}
	
	public enum SNAP {
		SnapGameState("1"),
		SnapTable("2"),
		SnapCards("3"),
		SnapWinPot("7"),
		SnapOddChips("8"),
		SnapPlayerAction("10"),
		SnapPlayerCurrent("11"),
		SnapPlayerShow("12"),
		SnapFoyer("16");
		
		private final String code;
		SNAP(String aCode) {
			code= aCode;
		}
		
		public static SNAP decode(String value) {
			for(SNAP aSNAP :SNAP.values()){
				if(aSNAP.code.equals(value)){
					return aSNAP;
				}
			}
			throw new IllegalArgumentException(
					String.format("The value %s is not found",value));
		}
	}
	protected Timer theTimer = new Timer();
	protected BufferedReader is;
	protected String id;
	
	private FileWriter writer;
	protected Gson parser = new Gson();
	private Map<String,String[]> playerCards = new HashMap<>();
	
	private Map<String,BET_TYPE> currentActionMap = new HashMap<>();
	private Map<String,BET_TYPE> previousActionMap = new HashMap<>();
	private Map<String, Integer> bigBlindMap = new HashMap<>();
	protected final BotInterface bot;
	private Map<String, Queue<HandEventBet>> logEvent = new HashMap<>();
	
	public  AbstractBotReceiver(final FileWriter writer, final BotInterface bot, final InputStream is) {
		this.writer = writer;
		this.bot = bot;
		this.is = new BufferedReader(new InputStreamReader(is));
		theTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				bot.requestGameList();
			}
		}, 5000, 5000);
	}
	
	@Override
	public void run() {
		try {
			String command = null;
			while ((command = is.readLine()) != null) {
				String[] parts = command.split(" ");
				switch (parts[0]) {
				case "PSERVER":
					id = parts[2];
					System.out.println("ID = "+id);
					break;
				case "GAMELIST":
					if (parts.length > 1) {
						for(int i = 1; i< parts.length; i++) {
							bot.requestGameInfo(parts[i]);
						}
					} 
					break;
				case "GAMEINFO":
					String table = parts[1];
					String[] tableInfo = parts[2].split(":");
					if("1".equals(tableInfo[2])){
						bot.registerGame(table);
						currentActionMap.put(table, BET_TYPE.NONE);
						previousActionMap.put(table, BET_TYPE.NONE);
					} 
					Integer bigBlind = new Integer(parts[3].split(":")[0]);
					bigBlindMap.put(table, bigBlind);
					break;
				case "SNAP": 
					decodeSnap(parts);
					break;
				default:
					break;
				}
			}
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	public void decodeSnap(String[] parts){
		SNAP snap = SNAP.decode(parts[2]);
		switch(snap){
		case SnapFoyer:
			bot.requestGameList();
			break;
		case SnapTable:
			respondeSnapTable(parts);
			break;
		case SnapCards:
			manageSnapCard(parts);
			break;
		case SnapPlayerAction:
			decodeActions(parts);
			break;
		case SnapWinPot:
			logEvents(parts);
		default:
		}
	}
	
	protected String getTable(String[] parts){
		return  parts[1].split(":")[0];
	}
	
	protected String[] getPlayerCard(String table){
		return playerCards.get(table);
	}
	
	protected void manageSnapCard(String[] parts){
		if("1".equals(parts[3])){
			String[] cards = new String[]{parts[4], parts[5]};
			playerCards.put(getTable(parts), cards);
		}
	}
	
	protected abstract BET_TYPE getType(HandEvent event);
	
	
	protected HandEvent createHandEvent(int handPhase,
										int previousBetType,
										int currentBetType,
										int pot,
										int turnPosition,
										String[] playerCards,
										String[] communityCards){
		Card[] playerCardsArray = new Card[2];
		for(int i=0; i<2; i++){
			playerCardsArray[i]= parseCard(playerCards[i]);
		}
		Card[] communityCardsArray = new Card[5];
		for(int i=0; i<communityCardsArray.length; i++){
			communityCardsArray[i]= new Card(-1,-1);
		}
		for(int i=0; i<communityCards.length; i++){
			communityCardsArray[i]= parseCard(communityCards[i]);
		}
		return new HandEvent(handPhase,previousBetType,currentBetType,pot,turnPosition,playerCardsArray,communityCardsArray);
	}

	
	protected void logHand(String table, HandEventBet event){
		if(!logEvent.containsKey(table)){
			logEvent.put(table , new LinkedList<HandEventBet>());
		}
		logEvent.get(table).offer(event);
	}
	
	private void logEvents(String parts[]){
		String table = getTable(parts);
		String player = parts[3];
		Queue<HandEventBet> queue = logEvent.remove(table);
		if(queue != null){
			while(!queue.isEmpty()) {
				HandEventBet event = queue.poll();
				if(!player.equals(id)){
					//I've lost so the best action was fold
					event.setCurrentActionDone(BET_TYPE.FOLD.ordinal()-1);
				}
				writer.writeString(parser.toJson(event));
			}
		}	
	}
	
	private Card parseCard(String card){
		SUITS suit = SUITS.evalute(card.charAt(card.length()-1));
		int cardValue = -1;
		String aCard = card.substring(0,card.length()-1);
		switch(aCard){
		case "K": cardValue = 12;
				break;
		case "Q": cardValue = 11;
				break;
		case "J": cardValue = 10;
				break;
		case "A": cardValue = 13;
				break;
		case "T": cardValue = 13;
				break;
		default: cardValue = new Integer(aCard);
		}
		return new Card(cardValue -1, suit.ordinal());
	}
	
	protected void decodeActions(String[] parts){
		String table = getTable(parts);
		int action = new Integer(parts[3]);
		String player = parts[4];
		BET_TYPE type = null;
		if(action == 6){
			type = BET_TYPE.RAISE;
		}else {
			type = BET_TYPE.values()[action];
		}
		if(id.equals(player)){
			previousActionMap.put(table, type);
		}else{
			currentActionMap.put(table, type);
			
		}
	}

	protected boolean checkBet(int current, String[] parts) {
		int index = 6;
		boolean found = false;
		while(true){
			String seat = parts[index++];
			if(!seat.startsWith("s")) {
				break;
			}
			if(seat.startsWith("s"+current)){
				found |= seat.split(":")[1].equals(id);
			}
		}
		return found;
	}

	public void respondeSnapTable(String[] parts) {
		int i =0;
		String[] tableState = parts[3].split(":");
		
		String[] communityCard = parts[5].split(":");
		String table = getTable(parts);
		int handPhase = 0;
		if(communityCard.length >= 4){
			handPhase = communityCard.length -3;
		}
		
		int minBet = new Integer(parts[parts.length-1]);
		
		int pot = calculatePot(parts);
		
		int round = Integer.parseInt(tableState[1]);
		int bigBlind = 0;
		int current = 0;
		String[] row = parts[4].split(":");
		if(row.length == 5) {
			String dealer = row[0];
			int smallBlind = Integer.parseInt(row[1]);
			bigBlind = Integer.parseInt(row[2]);
			current = new Integer(row[3]);
			int lastBet = new Integer(row[4]);
		}
		boolean imBetting = checkBet(current, parts);
		if(round >= 0 && imBetting) {
			System.out.println("Check action on table:"+table);
			String[] communityCards = Arrays.copyOfRange(communityCard, 1, communityCard.length);
			HandEvent event = this.createHandEvent(handPhase, previousActionMap.get(table).ordinal()-1, 
					currentActionMap.get(table).ordinal()-1, pot,0 , getPlayerCard(table), communityCards);
			BET_TYPE type = getType(event);
			System.out.println("Received Event data:"+type);
			switch(type){
			case FOLD:
				bot.fold(table);
				break;
			case BET:
				bot.raise(table, minBet );
				break;
			case CALL:
				bot.call(table);
				break;
			case CHECK:
				bot.call(table);
				break;
			case RAISE:
				bot.raise(table,Math.max(bigBlindMap.get(table),minBet));
				break;
			default:
				bot.allIn(table);
			}
			//remap allin as RAISE
			if(type == BET_TYPE.NONE){
				type = BET_TYPE.RAISE;
			}
			HandEventBet eventBet = new HandEventBet(event.getHandPhase(), 
					event.getPreviousActionDone(), 
					event.getOpponentActionDone(),  
					event.getPot(),
					event.getTurnPosition() , 
					event.getPlayerCards(), 
					event.getCommunityCards(), 
					type.ordinal()-1);
			logHand(table,eventBet);
		}
	}

	private int calculatePot(String[] parts) {
		boolean foundPot =false;
		int pot = 0;
		int index = 6;
		while(!foundPot){
			if(!parts[index].startsWith("s")) {
				foundPot=true;
				if(StringUtils.isEmpty(parts[index])){
					//skip empty part
					index++;
				}
				while(parts[index].startsWith("p")){
					pot += new Integer(parts[index++].split(":")[1]);
				}
			}
			index++;
		}
		return pot;
	}

}
